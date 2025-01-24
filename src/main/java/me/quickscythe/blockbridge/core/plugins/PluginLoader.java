package me.quickscythe.blockbridge.core.plugins;

import me.quickscythe.blockbridge.core.BridgeIntegration;

import javax.swing.*;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class PluginLoader {

    private final Map<Plugin, ClassLoader> plugins = new HashMap<>();
    private final BridgeIntegration integration;

    private final String pluginFolder = "plugins";

    private boolean enabled = false;

    public PluginLoader(BridgeIntegration integration) {
        this.integration = integration;
    }

    public void initialize() {
        if (!folder().exists()) integration().log("PluginLoader", "Creating plugin folder: " + folder().mkdirs());

    }

    public void enable() {
        if (enabled) throw new IllegalStateException("PluginLoader is already enabled.");
        enabled = true;
        registerPlugins();
        enablePlugins();
    }
    
    public BridgeIntegration integration() {
        return integration;
    }

    public Plugin registerPlugin(File pluginFile) {
        if (pluginFile.getName().endsWith(".jar")) {
            try {
                URLClassLoader classLoader = new URLClassLoader(new URL[]{pluginFile.toURI().toURL()});
                Properties properties = new Properties();

                InputStream inputStream = classLoader.getResourceAsStream("bot.plugin.properties");
                if (inputStream == null)
                    throw new IOException("bot.plugin.properties not found in the JAR file");

                properties.load(inputStream);
                if (!properties.containsKey("main"))
                    throw new IOException("Plugin " + pluginFile.getName() + " does not have a main class listed in bot.plugin.properties");
                if (!properties.containsKey("name"))
                    throw new IOException("Plugin " + pluginFile.getName() + " does not have a name listed in bot.plugin.properties");
                Class<Plugin> loadedClass = (Class<Plugin>) classLoader.loadClass(properties.getProperty("main"));
                Plugin instance = loadedClass.getDeclaredConstructor(String.class).newInstance(properties.getProperty("name"));
                classLoader.close();
                instance.logger().info("Initialized plugin {}.", instance.name());
                plugins.put(instance, classLoader);
                return instance;

            } catch (Exception e) {
                integration().log("PluginLoader", "There was an error registering plugin " + pluginFile.getName() + ".");
            }

        }
        throw new IllegalArgumentException("Plugin file must be a JAR file.");
    }

    public void enablePlugin(Plugin plugin) {
        integration().log("PluginLoader", "Enabling plugin " + plugin.name() + "...");
        plugin.enable();
        integration().log("PluginLoader", "Enabled " + plugin.name() + ".");
    }

    private void registerPlugins() {
        File plugin_folder = folder();
        integration().log("PluginLoader", "Initializing plugins...");
        for (File file : Objects.requireNonNull(plugin_folder.listFiles())) {
            registerPlugin(file);
        }
        integration().log("PluginLoader", "Initialized " + plugins.size() + " plugins.");
    }

    public Set<Plugin> getPlugins() {
        return plugins.keySet();
    }

    public Plugin getPlugin(String name) {
        for (Plugin plugin : getPlugins()) {
            if (plugin.name().equalsIgnoreCase(name)) return plugin;
        }
        return null;
    }

    public void disablePlugins() {
        integration().log("PluginLoader", "Disabling plugins...");
        for (Map.Entry<Plugin, ClassLoader> entry : plugins.entrySet()) {
            try {
                disablePlugin(entry.getKey());

            } catch (IOException e) {
                integration().log("PluginLoader", "There was an error disabling a plugin (" + entry.getKey().name() + ").");
            }
        }
        plugins.clear();
    }

    public void disablePlugin(Plugin plugin) throws IOException {
        plugin.disable();
        //todo remove any listeners
        String name = plugin.name();
        if (plugins.get(plugin) instanceof Closeable closeable) closeable.close();
        plugins.remove(plugin);
        integration().log("PluginLoader", "Plugin " + name + " disabled.");
    }

    private void enablePlugins() {
        for (Plugin plugin : getPlugins()) {
            enablePlugin(plugin);
        }
    }

    public void reloadPlugins() {

        disablePlugins();
        registerPlugins();
        enablePlugins();
    }

    public File folder() {
        return new File(integration.dataFolder(), pluginFolder);
    }


}
