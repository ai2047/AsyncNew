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

@WebServlet(urlPatterns = "/dispatch", asyncSupported = true)
public class DispatchAsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext ctx = req.startAsync();

        ctx.addListener(new AsyncListener() {
            public void onComplete(AsyncEvent event) {
                log("DispatchAsyncServlet onComplete called, thread id:  " + Thread.currentThread().getId());
            }
            public void onTimeout(AsyncEvent event) {
                log("DispatchAsyncServlet onTimeout called, thread id:  " + Thread.currentThread().getId());
            }
            public void onError(AsyncEvent event) {
                log("DispatchAsyncServlet onError called , thread id: " + Thread.currentThread().getId());
            }
            public void onStartAsync(AsyncEvent event) {
                log("DispatchAsyncServlet onStartAsync called, thread id:  " + Thread.currentThread().getId());
            }
        });

        ctx.dispatch("/simple");

    }
}
