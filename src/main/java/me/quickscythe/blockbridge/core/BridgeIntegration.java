package me.quickscythe.blockbridge.core;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.quickscythe.blockbridge.core.event.EventHandler;
import me.quickscythe.blockbridge.core.plugins.PluginLoader;
import me.quickscythe.blockbridge.core.server.BridgeHandler;
import me.quickscythe.blockbridge.core.server.BridgeServer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;

public abstract class BridgeIntegration {

    private final BridgeServer server;


    private final EventHandler handler;
    private final PluginLoader loader;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    public BridgeIntegration(Optional<BridgeServer.ServerConfig> serverConfig) {
        handler = new EventHandler(this);
        loader = new PluginLoader(this);


        this.server = serverConfig.map(config -> new BridgeServer(this, config)).orElse(null);
    }

    public static void main(String[] args) {
        BridgeServer.ServerConfig config = new BridgeServer.ServerConfig(BridgeServer.ServerProtocol.HTTP, "127.0.0.1", 9009);
        BridgeIntegration integration = new BridgeIntegration(Optional.of(config)) {
            @Override
            public void enable() {
                log(name(), "Enabled");
                BridgeHandler handler = new BridgeHandler(server()) {
                    @Override
                    public void doHandle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                        super.doHandle(target, baseRequest, request, response);
                        System.out.println("HANDLE?!");
                    }
                };
                handler.setContextPath("/test/");
                handler.setResourceBase("run/data/web");
                handler.addServlet(DefaultServlet.class, "/");
                try {
                    server().start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public File dataFolder() {
                return new File("data");
            }

            @Override
            public String name() {
                return "Test";
            }

            @Override
            public String version() {
                return "1.0";
            }
        };
        integration.enable();
    }

    public final EventHandler events() {
        return handler;
    }

    public final PluginLoader loader() {
        return loader;
    }

    public final Logger logger() {
        return logger;
    }

    public BridgeServer server() {
        return server;
    }

    public void destroy() throws IOException {
        if (dataFolder() != null && dataFolder().exists()) {
            Files.walk(dataFolder().toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            log(name(), "Folder deleted");
        }
    }


//    public abstract Logger logger();

    public abstract void enable();

    /**
     * Log a message to the console
     *
     * @param tag     The config that the message is from
     * @param message The message to log
     */
    public void log(String tag, String message) {
        logger.info("[" + tag + "] " + message);
    }

    /**
     * Get the data folder for the plugin
     *
     * @return The data folder
     */
    public abstract File dataFolder();

    /**
     * Get the name of the plugin
     *
     * @return The name of the plugin
     */
    public abstract String name();

    public abstract String version();


}
