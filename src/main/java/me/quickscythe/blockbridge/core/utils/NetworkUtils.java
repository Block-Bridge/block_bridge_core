/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package me.quickscythe.blockbridge.core.utils;


import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Logger;


/**
 * A utility class for network operations
 */
public class NetworkUtils {

    /**
     * Private constructor to prevent instantiation
     */
    private NetworkUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Downloads a file from a URL
     *
     * @param url  The URL to download from
     * @param auth The authentication to use
     * @return The InputStream of the file
     */
    public static InputStream downloadFile(String url, String... auth) {
        try {
            URL myUrl = new URI(url).toURL();
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setDoOutput(true);
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestMethod("GET");

            if (auth != null && auth.length >= 2) {
                String userCredentials = auth[0].trim() + ":" + auth[1].trim();
                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
                conn.setRequestProperty("Authorization", basicAuth);
            }
            return conn.getInputStream();
        } catch (Exception ex) {
            Logger.getLogger("Core").info("An error occurred while downloading file");
        }
        return InputStream.nullInputStream();
    }

    private static String inputStreamToString(InputStream inputStream) {
        try {

            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append(System.lineSeparator());
                }
            }
            return stringBuilder.toString();
        } catch (Exception ex) {
            Logger.getLogger("Network").info("An error occurred while downloading file");
        }
        return null;
    }

    public static String request(String url, String... auth) {
        return inputStreamToString(downloadFile(url, auth));
    }

    public static String post(String url, JSONObject data, String... auth) {
        try {
            URL myUrl = new URI(url).toURL();
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setDoOutput(true);
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestMethod("POST");

            if (auth != null && auth.length >= 2) {
                String userCredentials = auth[0].trim() + ":" + auth[1].trim();
                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
                conn.setRequestProperty("Authorization", basicAuth);
            }
            conn.getOutputStream().write(data.toString().getBytes());
            return inputStreamToString(conn.getInputStream());
        } catch (Exception ex) {
            Logger.getLogger("Network").info("An error occurred while downloading file");
        }
        return null;
    }

    /**
     * Saves an InputStream to a FileOutputStream
     *
     * @param in  The InputStream to save
     * @param out The FileOutputStream to save to
     */
    public static void saveStream(InputStream in, FileOutputStream out) {
        try {
            int c;
            byte[] b = new byte[1024];
            while ((c = in.read(b)) != -1) out.write(b, 0, c);

            in.close();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger("Core").info("An error occurred while saving file");
        }
    }
}
