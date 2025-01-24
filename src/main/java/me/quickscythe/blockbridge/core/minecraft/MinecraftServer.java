package me.quickscythe.blockbridge.core.minecraft;

import org.json.JSONObject;

import java.sql.ResultSet;

public class MinecraftServer {

    private final String name;
    private final String ip;
    private final int port;
    private final String motd;
    private final int onlinePlayers;
    private final int maxPlayers;


    public MinecraftServer(JSONObject jsonObject) {
        this.name = jsonObject.getString("name");
        this.ip = jsonObject.getString("ip");
        this.port = jsonObject.getInt("port");
        this.motd = jsonObject.getString("motd");
        this.onlinePlayers = jsonObject.getInt("onlinePlayers");
        this.maxPlayers = jsonObject.getInt("maxPlayers");

    }



    public String getMotd() {
        return motd;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getServerAddress() {
        return ip + ":" + port;
    }


}
