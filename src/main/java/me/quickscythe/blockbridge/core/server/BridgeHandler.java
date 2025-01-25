package me.quickscythe.blockbridge.core.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;

public abstract class BridgeHandler implements HttpHandler {

    BridgeServer server;

    public BridgeHandler(BridgeServer server){
        this.server = server;
    }

    public BridgeServer server() {
        return server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        server.integration().logger().info("Handling request: {}", exchange.getRequestURI());
        handle(exchange.getHttpContext(), exchange.getRequestURI(), exchange);
    }

    public abstract void handle(HttpContext context, URI uri, HttpExchange exchange) throws IOException;




}
