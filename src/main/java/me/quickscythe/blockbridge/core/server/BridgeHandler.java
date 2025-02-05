package me.quickscythe.blockbridge.core.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

public class BridgeHandler extends ServletContextHandler {

    BridgeServer server;

    public BridgeHandler(BridgeServer server){
        super(SESSIONS);
        this.server = server;
        server.setHandler(this);
    }

    public BridgeServer server() {
        return server;
    }

    public void handle(String name, BridgeServlet servlet, String path){
        path = path.startsWith("/") ? path.substring(1) : path;
        String url = server.integration().server().config().address();
        addServlet(new ServletHolder(name, servlet), "/" + server.integration().version() + "/" + path);
        server.integration().logger().info("Added servlet {} at: {}/{}/{}", name, url, server.integration().version(), path);
    }


    @Override
    public void doHandle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        super.doHandle(target, baseRequest, request, response);
    }
}
