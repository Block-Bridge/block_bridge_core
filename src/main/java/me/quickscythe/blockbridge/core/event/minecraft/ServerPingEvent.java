package me.quickscythe.blockbridge.core.event.minecraft;

import me.quickscythe.api.event.Event;
import me.quickscythe.blockbridge.core.minecraft.MinecraftServer;
import me.quickscythe.api.object.Player;

public class ServerPingEvent extends Event {

    private Player player;
    private MinecraftServer server;

    public ServerPingEvent(Player player, MinecraftServer server) {
        this.player = player;
        this.server = server;
    }

    public Player getPlayer() {
        return player;
    }

    public MinecraftServer getServer() {
        return server;
    }

}
