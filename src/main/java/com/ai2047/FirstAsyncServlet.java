package com.mantiso;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

@WebServlet(urlPatterns = "/simple", asyncSupported = true)
public class FirstAsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log("FirstAsyncServlet in 'doGet', thread id: " + Thread.currentThread().getId());

        final AsyncContext ctx = req.startAsync();

        ctx.setTimeout(3000);

        ctx.addListener(new AsyncListener() {
            public void onComplete(AsyncEvent event) {
                log("FirstAsyncServlet onComplete called, thread id:  " + Thread.currentThread().getId());
            }

            public void onTimeout(AsyncEvent event) {
                log("FirstAsyncServlet onTimeout called, thread id:  " + Thread.currentThread().getId());
            }

            public void onError(AsyncEvent event) {
                log("FirstAsyncServlet onError called , thread id: " + Thread.currentThread().getId());
            }

            public void onStartAsync(AsyncEvent event) {
                log("FirstAsyncServlet onStartAsync called, thread id:  " + Thread.currentThread().getId());
            }
        });

        ctx.start(() -> {
            try {
                log("FirstAsyncServlet in 'start', thread id: " + Thread.currentThread().getId());

                ctx.getResponse().getWriter().write(
                        MessageFormat.format(
                                "<h1>Processing task in bgt_id:[{0}]</h1>",
                                Thread.currentThread().getId()));

                Thread.sleep(5000);
            } catch (Exception e) {
                log("FirstAsyncServlet Problem processing task", e);
            }

            ctx.complete();

        });


    }
}
