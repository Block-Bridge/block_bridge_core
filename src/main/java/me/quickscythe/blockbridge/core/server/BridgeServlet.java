package me.quickscythe.blockbridge.core.server;

import jakarta.servlet.http.HttpServlet;

public class BridgeServlet extends HttpServlet {

    private final BridgeServer server;

    public BridgeServlet(BridgeServer server) {
        this.server = server;
    }

    public BridgeServer server() {
        return server;
    }



}
