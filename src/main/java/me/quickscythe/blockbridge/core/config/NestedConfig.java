package me.quickscythe.blockbridge.core.config;

import me.quickscythe.blockbridge.core.BridgeIntegration;
import org.json.JSONObject;

import java.io.File;

public abstract class NestedConfig<T extends Config> extends Config{

    private final T parent;
    /**
     * Creates a new nested config file
     *
     * @param name        The name of the config
     * @param integration The plugin that owns this config
     */
    public NestedConfig(T parent, String name, BridgeIntegration integration) {
        super(null, name, integration);
        this.parent = parent;
    }

    public Config parent() {
        return parent;
    }

    @Override
    public void save() {
        parent.save();
    }

    @Override
    public JSONObject json() {
        JSONObject json = super.json();
        json.put("nested_config_parent", parent().name());
        return json;
    }
}
