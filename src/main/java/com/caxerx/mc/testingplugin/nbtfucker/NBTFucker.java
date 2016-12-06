package com.caxerx.mc.testingplugin.nbtfucker;

import com.comphenix.protocol.wrappers.nbt.NbtBase;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by caxerx on 2016/9/1.
 */
public class NBTFucker extends JavaPlugin implements Listener {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onOn9(InventoryCloseEvent e) {
        PlayerInventory inv = e.getPlayer().getInventory();
        for (int i = 0; i < inv.getSize() - 1; i++) {
            ItemStack item = inv.getItem(i);
            if (item != null) {
                NbtFactory.setItemTag(item, null);
                inv.setItem(i, item);
                getLogger().info("wow");
            }
        }
    }
}
