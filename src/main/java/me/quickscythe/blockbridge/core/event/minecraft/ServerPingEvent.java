package me.quickscythe.blockbridge.core.event.minecraft;

import me.quickscythe.blockbridge.core.event.BridgeEvent;
import me.quickscythe.blockbridge.core.event.listener.Listener;
import me.quickscythe.blockbridge.core.minecraft.MinecraftServer;
import me.quickscythe.blockbridge.core.minecraft.Player;

public record ServerPingEvent(Player player, MinecraftServer server) implements BridgeEvent {

    @Override
    public Class<? extends Listener> listener() {
        return Listener.PingListener.class;
    }
}
