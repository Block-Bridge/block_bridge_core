package me.quickscythe.blockbridge.core;

import me.quickscythe.blockbridge.core.event.EventHandler;
import me.quickscythe.blockbridge.core.plugins.PluginLoader;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;

public abstract class BridgeIntegration {

    private final EventHandler handler;
    private final PluginLoader loader;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    public BridgeIntegration(){
        handler = new EventHandler(this);
        loader = new PluginLoader(this);
    }

    public final EventHandler events(){
        return handler;
    }

    public final PluginLoader loader(){
        return loader;
    }

    public final Logger logger(){
        return logger;
    }

    public void destroy() throws IOException {
        if(dataFolder() != null && dataFolder().exists()){
            Files.walk(dataFolder().toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            log(name(), "Folder deleted");
        }
    }


    public abstract void enable();


//    public abstract Logger logger();
    /**
     * Log a message to the console
     *
     * @param tag The config that the message is from
     * @param message           The message to log
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


}
