package com.caxerx.mc.testingplugin.worldanimation;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by caxerx on 2016/8/13.
 */
public class AnimationEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public AnimationEvent(AnimationPlayer player){

    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


}
