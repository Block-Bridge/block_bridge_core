package me.quickscythe.blockbridge.core.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.IOException;
import java.net.URI;

public abstract class BridgeHandler extends ServletContextHandler {

    BridgeServer server;

    public BridgeHandler(BridgeServer server){
        super(SESSIONS);
        this.server = server;
        server.setHandler(this);
    }

    public BridgeServer server() {
        return server;
    }


    @Override
    public void doHandle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        super.doHandle(target, baseRequest, request, response);
    }
}
