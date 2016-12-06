package com.caxerx.mc.testingplugin.crafting;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by caxerx on 2016/8/16.
 */
public class InventoryUpdateRunnable extends BukkitRunnable {
    HumanEntity player;
    Inventory inventory;

    public InventoryUpdateRunnable(HumanEntity player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        ItemStack[] items = inventory.getContents();
        if (items[29] != null && items[38] != null && items[30] != null && items[28] != null && items[20] != null && items[11] != null && items[2] != null && items[29].getType() == Material.EMERALD && items[2].getType() == Material.IRON_INGOT && items[11].getType() == Material.IRON_INGOT && items[20].getType() == Material.IRON_INGOT && items[28].getType() == Material.IRON_INGOT && items[30].getType() == Material.IRON_INGOT && items[38].getType() == Material.IRON_INGOT) {
            inventory.setItem(34, new ItemStack(Material.IRON_SWORD));
        } else {
            inventory.setItem(34, null);
        }
        if (items[8] != null && items[8].getType() == Material.PAPER) {
            inventory.setItem(51, new ItemStack(Material.BARRIER));
            inventory.setItem(52, new ItemStack(Material.BARRIER, 5));
            inventory.setItem(53, new ItemStack(Material.BARRIER, 10));
        } else {
            inventory.setItem(51, new ItemStack(Material.ANVIL));
            inventory.setItem(52, new ItemStack(Material.ANVIL, 5));
            inventory.setItem(53, new ItemStack(Material.ANVIL, 10));
        }
/*
        if (player.getItemOnCursor().getType() == Material.AIR) {
            CraftingSystem.flick.add(player);
            player.openInventory(inventory);
        }
        */
    }
}
