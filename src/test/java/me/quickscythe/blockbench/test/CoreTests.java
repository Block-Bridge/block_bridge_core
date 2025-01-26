package me.quickscythe.blockbench.test;

import me.quickscythe.blockbench.test.config.TestConfig;
import me.quickscythe.blockbench.test.factory.ObjectFactory;
import me.quickscythe.blockbridge.core.BridgeIntegration;
import me.quickscythe.blockbridge.core.config.Config;
import me.quickscythe.blockbridge.core.config.ConfigManager;
import me.quickscythe.blockbridge.core.plugins.Plugin;
import me.quickscythe.blockbridge.core.utils.NetworkUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CoreTests {

    Logger logger = LoggerFactory.getLogger(CoreTests.class);


//    @Test
//    void launchIntegration(){
//        logger.info("Starting test: launchIntegration");
//        BridgeIntegration integration = ObjectFactory.createIntegration();
//        launchIntegration(integration, "launchIntegration");
//    }

    @Test
    void testNetworkUtilsClass(){
        String request = NetworkUtils.request("http://www.google.com");

        assertNotNull(request, "Request was null");

        String post = NetworkUtils.post("http://www.google.com", new JSONObject().put("test", "test"));
        System.out.println(post);

    }

    @Test
    void launchIntegrationWithPluginsClearWhenDone() throws InterruptedException, IOException {
        long started = System.currentTimeMillis();
        logger.info("Starting test: launchIntegrationWithPluginsClearWhenDone");
        BridgeIntegration integration = ObjectFactory.createIntegration();
        integration.loader().initialize();
        launchIntegration(integration, "launchIntegrationWithPluginsClearWhenDone");

        try {
            String path = integration.loader().folder().getPath();
            logger.info("launchIntegrationWithPluginsClearWhenDone: path: {}", path);
            URL resource = CoreTests.class.getClassLoader().getResource("test_plugins");

            if (resource != null) {
                File folder = new File(resource.getFile());
                if (folder.isDirectory()) {
                    File[] files = folder.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (file.isFile() && file.getName().endsWith(".jar")) {
                                Files.copy(file.toPath(), new FileOutputStream(new File(integration.loader().folder(), file.getName())));
                                logger.info("launchIntegrationWithPluginsClearWhenDone: copied plugin: {}", file.getName());
                            } else if (file.isDirectory()) {
                                System.out.println("Directory: " + file.getName());
                            }
                        }
                    }
                } else {
                    System.out.println("The provided path is not a directory.");
                }
            } else {
                System.out.println("Folder not found in resources.");
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("launchIntegrationWithPluginsClearWhenDone: enabling loader");
        integration.loader().enable();
        assertFalse(integration.loader().getPlugins().isEmpty(), "No plugins were loaded");
        List<Plugin> plugins = new ArrayList<>(integration.loader().getPlugins());
        for(Plugin plugin : plugins){
            integration.loader().disablePlugin(plugin);
        }
        plugins.clear();
        System.gc();

//        Thread.sleep(1000);


        destroyIntegration(integration, "launchIntegrationWithPluginsClearWhenDone");
        integration.log("TestIntegration", "Tests Complete! Time took: " + (System.currentTimeMillis() - started) + "ms");

        assertFalse(integration.dataFolder().exists());



    }

    void destroyIntegration(BridgeIntegration integration, String test) {
        try {
            logger.info("{}: destroying integration", test);
            integration.destroy();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void launchIntegrationClearWhenDone() {
        logger.info("Starting test: launchIntegrationClearWhenDone");
        long started = System.currentTimeMillis();

        BridgeIntegration integration = ObjectFactory.createIntegration();
        launchIntegration(integration, "launchIntegrationClearWhenDone");

        destroyIntegration(integration, "launchIntegrationClearWhenDone");
        integration.log("TestIntegration", "Tests Complete! Time took: " + (System.currentTimeMillis() - started) + "ms");

        assertFalse(integration.dataFolder().exists());
    }


    @Test
    void launchIntegrationTwiceClearWhenDone() throws InterruptedException {
        logger.info("Starting test: launchIntegrationTwiceClearWhenDone");
        long started = System.currentTimeMillis();

        BridgeIntegration integration = ObjectFactory.createIntegration();
        launchIntegration(integration, "launchIntegrationTwiceClearWhenDone");
        TestConfig config = ConfigManager.getConfig(integration, TestConfig.class);
        config.testConfig.nestedValue = "Test";
        config.save();

        logger.info("launchIntegrationTwiceClearWhenDone: simulating shut-down");
        ConfigManager.reset();

        logger.info("launchIntegrationTwiceClearWhenDone: creating second integration");
        BridgeIntegration integration2 = ObjectFactory.createIntegration();
        launchIntegration(integration2, "launchIntegrationTwiceClearWhenDone");

        destroyIntegration(integration2, "launchIntegrationTwiceClearWhenDone");

        integration2.log("TestIntegration", "Tests Complete! Time took: " + (System.currentTimeMillis() - started) + "ms");

        assertFalse(integration2.dataFolder().exists(), "Data folder was not deleted");
    }

    void checkValueChanged(TestConfig config2) {
        assertEquals("Test", config2.testConfig.nestedValue, "Value did not save correctly");

    }

    void checkConfigContents(Config config) {
        assertFalse(config.json().isEmpty(), "Config is empty");
    }

    void checkConfigFiles(Config config) {
        assertTrue(config.file().exists(), "Config file does not exist");
    }

    void launchIntegration(BridgeIntegration integration, String test){
        integration.enable();
        TestConfig config = ConfigManager.getConfig(integration, TestConfig.class);
        logger.info("{}: Verifying config files exist", test);
        checkConfigFiles(config);

        logger.info("{}: Verifying config contents", test);
        checkConfigContents(config);
    }

}
