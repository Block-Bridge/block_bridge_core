package me.quickscythe.blockbridge.core.event.minecraft;

import me.quickscythe.api.event.Event;
import me.quickscythe.blockbridge.core.minecraft.MinecraftServer;

public class ServerStatusChangeEvent extends Event {

    private String status;
    private MinecraftServer server;

    public ServerStatusChangeEvent(String status, MinecraftServer server) {
        this.status = status;
        this.server = server;
    }

    public String getStatus() {
        return status;
    }

    public MinecraftServer getServer() {
        return server;
    }

}
