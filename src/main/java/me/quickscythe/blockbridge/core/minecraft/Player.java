package me.quickscythe.blockbridge.core.minecraft;

import org.json.JSONObject;

import java.util.UUID;

public class Player extends MinecraftEntity {

    public Player(JSONObject data) {
        super(data);
    }

    public Player(String name, UUID uid) {
        super(name, uid);
    }
}
