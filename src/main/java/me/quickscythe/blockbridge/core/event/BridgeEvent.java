package me.quickscythe.blockbridge.core.event;


import me.quickscythe.blockbridge.core.event.listener.Listener;

public interface BridgeEvent {

    Class<? extends Listener> listener();

}
