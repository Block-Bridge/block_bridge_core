package me.quickscythe.blockbridge.core.event.minecraft;


import me.quickscythe.blockbridge.core.event.BridgeEvent;
import me.quickscythe.blockbridge.core.event.listener.Listener;
import me.quickscythe.blockbridge.core.minecraft.Player;

public record PlayerLeaveEvent(Player player) implements BridgeEvent {

    @Override
    public Class<? extends Listener> listener() {
        return Listener.LeaveListener.class;
    }
}
