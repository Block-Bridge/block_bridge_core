package test;

import me.quickscythe.Api;
import me.quickscythe.api.v1.BlockBridgeApiV1;
import me.quickscythe.api.v2.BlockBridgeApiV2;
import org.apache.commons.io.output.TeeOutputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTests {

    Api bot;
    Api server;

    void launchApi(Api api, String search_string) throws InterruptedException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        TeeOutputStream teeOutputStream = new TeeOutputStream(originalOut, outputStream);
        System.setOut(new PrintStream(teeOutputStream));

        // Start the application or the code that generates the console output
        api.init(true);

        // Create a latch to wait for the output or timeout
        CountDownLatch latch = new CountDownLatch(1);

        // Create a thread to monitor the console output
        Thread monitorThread = new Thread(() -> {
            while (latch.getCount() > 0) {
                String output = outputStream.toString();
                if (output.contains(search_string)) {
                    latch.countDown();
                }
                try {
                    Thread.sleep(100); // Check every 100ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        monitorThread.start();

        // Wait for the latch to count down or timeout after 10 seconds
        boolean found = latch.await(10, TimeUnit.SECONDS);

        // Restore the original System.out
        System.setOut(originalOut);

        // Assert that the string "Done!" was found
        assertTrue(found, "The string \"" + search_string + "\" was not found in the console output within 10 seconds.");
    }

    @Test
    void launchBot() throws InterruptedException {
        bot = new BlockBridgeApiV2();
        launchApi(bot, "WebApp started on port");
    }

    @Test
    void launchServer() throws InterruptedException {
        server = new BlockBridgeApiV1();
        launchApi(server, "WebApp started on port");
    }
}
