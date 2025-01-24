package me.quickscythe.blockbench.test.config;

import me.quickscythe.blockbridge.core.BridgeIntegration;
import me.quickscythe.blockbridge.core.config.Config;
import me.quickscythe.blockbridge.core.config.ConfigTemplate;
import me.quickscythe.blockbridge.core.config.ConfigValue;
import org.json.JSONArray;

import java.io.File;

@ConfigTemplate(name = "test_config")
public class TestConfig extends Config {

    @ConfigValue
    public String testValue = "me/quickscythe/blockbench/test";

    @ConfigValue
    public int testInt = 1;

    @ConfigValue
    public boolean testBool = true;

    @ConfigValue
    public double testDouble = 1.0;

    @ConfigValue
    public float testFloat = 1.0f;

    @ConfigValue
    public long testLong = 1L;




    @ConfigValue
    public JSONArray testArray = new JSONArray().put(1).put("me/quickscythe/blockbench/test");

    @ConfigValue
    public TestNestedConfig<?> testConfig = null;

    /**
     * Creates a new config file
     *
     * @param file        The file to save to
     * @param name        The name of the config
     * @param integration The plugin that owns this config
     */
    public TestConfig(File file, String name, BridgeIntegration integration) {
        super(file, name, integration);
    }
}
