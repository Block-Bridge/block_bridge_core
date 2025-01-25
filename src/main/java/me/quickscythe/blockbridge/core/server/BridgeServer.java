package me.quickscythe.blockbridge.core.server;

import com.sun.net.httpserver.HttpServer;
import me.quickscythe.blockbridge.core.BridgeIntegration;

import java.io.IOException;
import java.net.InetSocketAddress;

public class BridgeServer {

    private final HttpServer server;
    private final BridgeIntegration integration;

    public BridgeServer(BridgeIntegration integration, ServerConfig config) {
        this.integration = integration;
        try {
            server = HttpServer.create(new InetSocketAddress(config.port()), 0);
            integration.logger().info("Starting server on {}", config.address());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public BridgeIntegration integration(){
        return integration;
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    public void handle(BridgeHandler handler, String... paths) {
        for (String path : paths){
            path = "/" + integration.version() + (path.startsWith("/") ? path : "/" + path);
            integration.logger().info("Registering handler for {}", path);
            server.createContext(path, handler);
        }
    }
    
    public record ServerConfig(ServerProtocol protocol, String host, int port) {

        public String address(){
            return protocol().prefix() + host() + ":" + port();
        }

    }
    
    public enum ServerProtocol {
        HTTP("http://"), HTTPS("https://");

        final String prefix;

        ServerProtocol(String prefix) {
            this.prefix = prefix;
        }

        public String prefix() {
            return prefix;
        }
    }
}
