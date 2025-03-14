package me.quickscythe.blockbench.test.factory;

import me.quickscythe.blockbench.test.config.TestConfig;
import me.quickscythe.blockbench.test.config.TestNestedConfig;
import me.quickscythe.blockbridge.core.BridgeIntegration;
import me.quickscythe.blockbridge.core.config.ConfigManager;
import me.quickscythe.blockbridge.core.server.BridgeServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

public class ObjectFactory {


    public static BridgeIntegration createIntegration() {
        return createIntegration("TestIntegration", "test_data");
    }

    public static BridgeIntegration createIntegration(String name, String folderPath) {
        return new BridgeIntegration(Optional.empty()) {

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

            @Override
            public String version() {
                return "1.0.1";
            }
        };
    }

    public static BridgeIntegration createIntegrationWithServer(BridgeServer.ServerConfig config, String name, String folderPath) {
        return new BridgeIntegration(Optional.of(config)) {

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

            @Override
            public String version() {
                return "1.0.1";
            }
        };
    }
}
