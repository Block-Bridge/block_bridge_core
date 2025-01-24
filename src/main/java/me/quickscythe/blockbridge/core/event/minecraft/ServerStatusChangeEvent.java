package me.quickscythe.blockbridge.core.event.minecraft;

import me.quickscythe.blockbridge.core.event.BridgeEvent;
import me.quickscythe.blockbridge.core.event.listener.Listener;
import me.quickscythe.blockbridge.core.minecraft.MinecraftServer;

public record ServerStatusChangeEvent(String status, MinecraftServer server) implements BridgeEvent {

    @Override
    public Class<? extends Listener> listener() {
        return Listener.StatusListener.class;
    }
}
