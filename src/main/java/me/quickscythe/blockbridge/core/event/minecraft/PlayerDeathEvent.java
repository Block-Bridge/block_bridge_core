package me.quickscythe.blockbridge.core.event.minecraft;

import me.quickscythe.blockbridge.core.event.BridgeEvent;
import me.quickscythe.blockbridge.core.event.listener.Listener;
import me.quickscythe.blockbridge.core.minecraft.MinecraftEntity;
import me.quickscythe.blockbridge.core.minecraft.Player;

import java.util.Optional;

public record PlayerDeathEvent(Player player, Optional<MinecraftEntity> killer, String message) implements BridgeEvent {
    @Override
    public Class<? extends Listener> listener() {
        return Listener.DeathListener.class;
    }
}
