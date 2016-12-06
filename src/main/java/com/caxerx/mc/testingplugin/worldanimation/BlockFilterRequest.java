package com.caxerx.mc.testingplugin.worldanimation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by caxerx on 2016/8/12.
 */
public class BlockFilterRequest extends BlockChangeRequest {
    Material original;

    public BlockFilterRequest(Player player, Location location, Material originalMaterial, Material replaceMaterial, byte data) {
        super(player, location, replaceMaterial, data);
        this.original = originalMaterial;
    }


    public void execute() {
        if (location.getBlock().getType() == original) {
            super.execute();
        }
    }
}
