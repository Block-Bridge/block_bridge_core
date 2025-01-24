package me.quickscythe.blockbridge.core.minecraft;

import org.json.JSONObject;

public class Player {
    private String name;
    private String uid;

    public Player(JSONObject data) {
        this.name = data.getString("username");
        this.uid = data.getString("uuid");
    }

    public String getName(){
        return name;
    }

    public String getUid(){
        return uid;
    }

}
