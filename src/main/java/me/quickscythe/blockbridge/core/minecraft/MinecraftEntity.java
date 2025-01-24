package me.quickscythe.blockbridge.core.minecraft;

import org.json.JSONObject;

import java.util.UUID;

public class MinecraftEntity {

    private final String name;
    private final UUID uid;

    public MinecraftEntity(JSONObject data) {
        this.name = data.getString("name");
        this.uid = UUID.fromString(data.getString("uuid"));
    }

    public MinecraftEntity(String name, UUID uid){
        this.name = name;
        this.uid = uid;
    }

    public String getName(){
        return name;
    }

    public UUID getUid(){
        return uid;
    }
}
