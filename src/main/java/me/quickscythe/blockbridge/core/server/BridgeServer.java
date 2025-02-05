package me.quickscythe.blockbridge.core.server;

import com.sun.net.httpserver.HttpServer;
import me.quickscythe.blockbridge.core.BridgeIntegration;
import org.eclipse.jetty.server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;

public class BridgeServer extends Server{

    private final BridgeIntegration integration;
    private final BridgeHandler handler;

    public BridgeServer(BridgeIntegration integration, ServerConfig config) {
        super(config.port());
        this.integration = integration;
        this.handler = new BridgeHandler(this);
    }

    public BridgeHandler handler(){
        return handler;
    }

    public BridgeIntegration integration(){
        return integration;
    }


    public void handle(BridgeHandler handler) {
        setHandler(handler);
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
