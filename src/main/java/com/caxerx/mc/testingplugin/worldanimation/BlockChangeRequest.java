package com.caxerx.mc.testingplugin.worldanimation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by caxerx on 2016/8/12.
 */
public class BlockChangeRequest {
    protected Player player;
    protected Location location;
    protected Material material;
    protected byte data;

    public BlockChangeRequest(Player player, Location location, Material material, byte data) {
        this.player = player;
        this.location = location;
        this.material = material;
        this.data = data;
    }

    public Player getPlayer() {
        return player;
    }

    public void execute() {
        player.sendBlockChange(location, material, data);
    }
}
