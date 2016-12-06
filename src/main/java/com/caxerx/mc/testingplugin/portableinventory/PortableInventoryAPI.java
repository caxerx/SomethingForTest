package com.caxerx.mc.testingplugin.portableinventory;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by caxerx on 2016/8/17.
 */
public class PortableInventoryAPI {
    private static Plugin plugin;
    private static PortableInventoryManager manager;
    private static boolean initialized = false;

    public PortableInventoryAPI(Plugin plugin, PortableInventoryManager manager) {
        this.plugin = plugin;
        this.manager = manager;
        initialized = true;
    }

    public static void openPortableInventory(Player player, int page) {
        if (initialized) {
            manager.getPlayerInventory(player).open(page);
        }
    }
}
