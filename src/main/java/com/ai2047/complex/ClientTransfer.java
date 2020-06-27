package com.mantiso.complex;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebListener;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@WebListener
public class ClientTransfer implements ServletContextListener {
    private static final int CLIENT_THREAD_COUNT = 10;
    private final Executor executor = Executors.newFixedThreadPool(CLIENT_THREAD_COUNT);
    private static final BlockingQueue<Client> Clients = new LinkedBlockingQueue<>();

    static void addClient(Client client) {
        Clients.add(client);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        int count = 0;
        while (count < CLIENT_THREAD_COUNT) {
            executor.execute(() -> {
                sendDataToClient();
            });
            count++;
        }
    }

    private void sendDataToClient() {
        while (true) {
            Client client;

            client = getClient();

            AsyncContext asyncContext = client.getAsyncContext();
            ServletResponse response = asyncContext.getResponse();
            response.setContentType("text/plain");
            getDataToSend(client);

            sendDataChunk(client, asyncContext, response);
        }
    }

    private void sendDataChunk(Client client, AsyncContext asyncContext, ServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.print("Sending " + client.getBytesSent() + " bytes\r\n");

            // check if we have done the work
            if (client.isWorkDone()) {
                // if the 100 bytes are sent, the response is complete
                asyncContext.complete();
            } else {
                // if not, put the client again in the queue
                Clients.put(client);
            }

        } catch(Exception e) {
            asyncContext.complete();
        }

    }

    private void getDataToSend(Client client) {
        client.doWork();
    }

    private Client getClient() {
        Client client;
        try {
            // fetch a remote client from the waiting queue
            // (this call blocks until a client is available)
            client = Clients.take();
        } catch (InterruptedException e1) {
            throw new RuntimeException("Interrupted while waiting for remote clients");
        }
        return client;
    }
}
