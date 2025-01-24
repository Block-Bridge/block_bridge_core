package me.quickscythe.blockbridge.core.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Plugin {

    private final String name;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Plugin(String name){
        this.name = name;
    }


    public abstract void enable();

    public abstract void disable();

    public String name() {
        return name;
    }


    public Logger logger(){
        return logger;
    }
}
