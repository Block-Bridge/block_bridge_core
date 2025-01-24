package me.quickscythe.blockbridge.core.event.api;

import me.quickscythe.blockbridge.core.event.BridgeEvent;
import me.quickscythe.blockbridge.core.event.listener.Listener;
import org.json.JSONObject;

public record ApiChannelMessageEvent() implements BridgeEvent {


    @Override
    public Class<? extends Listener> listener() {
        return Listener.ApiChannelListener.class;
    }
}
