package com.mantiso.complex;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/download", asyncSupported = true)
public class TransferDataAsyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) {
        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(15 * 60 * 1000);
        ClientTransfer.addClient(new Client(asyncContext));
    }
}
