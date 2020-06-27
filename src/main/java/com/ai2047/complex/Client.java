package com.mantiso.complex;

import javax.servlet.AsyncContext;

public class Client {

    private final AsyncContext asyncContext;
    private int bytesSent;

    public Client(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }


    public AsyncContext getAsyncContext() {
        return asyncContext;
    }

    public void doWork() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            throw new RuntimeException(e1);
        }

        this.bytesSent += 10;

    }

    public int getBytesSent() {
        return bytesSent;
    }

    public boolean isWorkDone() {
        return bytesSent >= 100;
    }
}
