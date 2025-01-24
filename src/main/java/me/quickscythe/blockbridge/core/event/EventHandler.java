package me.quickscythe.blockbridge.core.event;


import me.quickscythe.blockbridge.core.BridgeIntegration;
import me.quickscythe.blockbridge.core.event.api.ApiChannelMessageEvent;
import me.quickscythe.blockbridge.core.event.listener.Listener;
import me.quickscythe.blockbridge.core.event.minecraft.PlayerChatEvent;
import me.quickscythe.blockbridge.core.event.minecraft.ServerPingEvent;
import me.quickscythe.blockbridge.core.event.minecraft.ServerStatusChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {

    private final BridgeIntegration integration;
    private final List<Listener> listeners = new ArrayList<>();

    public EventHandler(BridgeIntegration integration){
        this.integration = integration;
    }

    public void register(Listener listener){
        listeners.add(listener);
    }

    public void handle(BridgeEvent event){
        for(Listener listener : listeners){
            if(event.listener().isAssignableFrom(listener.getClass())){
                if(event.listener().equals(Listener.ApiChannelListener.class)) ((Listener.ApiChannelListener) listener).onMessage((ApiChannelMessageEvent) event);
                if(event.listener().equals(Listener.StatusListener.class)) ((Listener.StatusListener) listener).onStatusChange((ServerStatusChangeEvent) event);
                if(event.listener().equals(Listener.PingListener.class)) ((Listener.PingListener) listener).onPing((ServerPingEvent) event);
                if(event.listener().equals(Listener.JoinListener.class)) ((Listener.JoinListener) listener).onJoin((me.quickscythe.blockbridge.core.event.minecraft.PlayerJoinEvent) event);
                if(event.listener().equals(Listener.LeaveListener.class)) ((Listener.LeaveListener) listener).onLeave((me.quickscythe.blockbridge.core.event.minecraft.PlayerLeaveEvent) event);
                if(event.listener().equals(Listener.DeathListener.class)) ((Listener.DeathListener) listener).onDeath((me.quickscythe.blockbridge.core.event.minecraft.PlayerDeathEvent) event);
                if(event.listener().equals(Listener.ChatListener.class)) ((Listener.ChatListener) listener).onPlayerChat((PlayerChatEvent) event);
                if(event.listener().equals(Listener.CommandListener.class)) ((Listener.CommandListener) listener).onCommand((me.quickscythe.blockbridge.core.event.minecraft.PlayerCommandEvent) event);
            }
        }
    }
}
