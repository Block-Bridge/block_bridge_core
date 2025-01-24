package me.quickscythe.blockbench.test.factory;

import me.quickscythe.blockbench.test.config.TestConfig;
import me.quickscythe.blockbench.test.config.TestNestedConfig;
import me.quickscythe.blockbridge.core.BridgeIntegration;
import me.quickscythe.blockbridge.core.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ObjectFactory {


    public static BridgeIntegration createIntegration(){
        return createIntegration("TestIntegration", "test_data");
    }

    public static BridgeIntegration createIntegration(String name, String folderPath){
        return new BridgeIntegration() {

            final Logger logger = LoggerFactory.getLogger(this.getClass());
            File file = new File(folderPath);


            @Override
            public void enable() {

                if (!file.exists())
                    log("TestIntegration", "Data folder does not exist. Creating... " + (file.mkdirs() ? "Success" : "Failed"));

                TestConfig config = ConfigManager.registerConfig(this, TestConfig.class);
                config.testConfig = ConfigManager.getNestedConfig(config, TestNestedConfig.class, "testConfig");
                config.save();
                log("TestIntegration", "Enabled");

            }



            @Override
            public File dataFolder() {
                return file;
            }

            @Override
            public String name() {
                return name;
            }
        };
    }
}
