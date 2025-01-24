package me.quickscythe.blockbench.test.config;

import me.quickscythe.blockbridge.core.BridgeIntegration;
import me.quickscythe.blockbridge.core.config.Config;
import me.quickscythe.blockbridge.core.config.ConfigTemplate;
import me.quickscythe.blockbridge.core.config.ConfigValue;
import me.quickscythe.blockbridge.core.config.NestedConfig;

import java.io.File;

@ConfigTemplate(name = "nested")
public class TestNestedConfig<T extends Config> extends NestedConfig<T> {

    @ConfigValue
    public String nestedValue = "nestedValue";

    @ConfigValue
    public int nestedInt = 1;

    /**
     * Creates a new config file
     *
     * @param parent      The Config instance that holds this nested config.
     * @param name        The name of the config
     * @param integration The plugin that owns this config
     */
    public TestNestedConfig(T parent, String name, BridgeIntegration integration) {
        super(parent, name, integration);
    }
}
