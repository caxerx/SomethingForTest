package com.caxerx.mc.testingplugin.worldanimation;

import org.bukkit.entity.Player;

/**
 * Created by caxerx on 2016/8/13.
 */
public class AnimationPlayer {
    private final Player player;

    public AnimationPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
