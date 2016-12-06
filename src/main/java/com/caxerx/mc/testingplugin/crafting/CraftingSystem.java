package com.caxerx.mc.testingplugin.crafting;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caxerx on 2016/8/15.
 */
public class CraftingSystem extends JavaPlugin implements Listener {
    HashMap<String, Inventory> invs;
    HashMap<Player, BukkitTask> opens;

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        //inv = getServer().createInventory(null, 54, "TESTING");
        invs = new HashMap<>();
        opens = new HashMap<>();
        getConfig().getKeys(false).forEach(invname -> {
            Class theclass = new ArrayList<Map<String, Object>>().getClass();
            ArrayList<Map<String, Object>> lists = (ArrayList<Map<String, Object>>) new Gson().fromJson(getConfig().getString(invname), theclass);
            List<ItemStack> listitemstack = new ArrayList<>();
            lists.forEach(itemstack -> listitemstack.add(itemstack != null ? ItemStack.deserialize(itemstack) : null));
            Inventory inv = getServer().createInventory(null, 54, invname);
            inv.setContents(listitemstack.toArray(new ItemStack[54]));
            invs.put(invname, inv);
        });
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (e.getMessage().startsWith("inv ")) {
            String target = e.getMessage().replaceFirst("inv ", "");
            if (invs.containsKey(target)) {
                e.getPlayer().openInventory(invs.get(target));
            } else {
                invs.put(target, getServer().createInventory(null, 54, target));
                e.getPlayer().openInventory(invs.get(target));
            }
        }
        if (e.getMessage().equalsIgnoreCase("crafting")) {
            Inventory inv = getServer().createInventory(null, 54, "Crafting");
            inv.setContents(invs.get("合成界面").getContents());
            e.getPlayer().openInventory(inv);
        }
    }

    @EventHandler
    public void onPlayerLeave(InventoryCloseEvent e) {
        invs.forEach((name, inv) -> {
            List<Map<String, Object>> face = new ArrayList<>();
            Lists.newArrayList(inv.getContents()).forEach(stack -> {
                if (stack != null) {
                    face.add(stack.serialize());
                } else {
                    face.add(null);
                }
            });
            getConfig().set(name, new Gson().toJson(face));
            saveConfig();
        });
    }

    @EventHandler
    public void onPlayerCraft(InventoryClickEvent e) {
        e.getWhoClicked().sendMessage(e.getEventName());
        fuckOff(e);
    }

    @EventHandler
    public void onPlayerCraft(InventoryDragEvent e) {
        e.getWhoClicked().sendMessage(e.getEventName());
        fuckOff(e);
    }

    @EventHandler
    public void onPlayerCraft(InventoryInteractEvent e) {
        e.getWhoClicked().sendMessage(e.getEventName());
        fuckOff(e);
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent e) {
        e.setCancelled(false);
        e.setTo(e.getPlayer().getLocation().add(0, 40, 0));
    }

    private void fuckOff(InventoryInteractEvent e) {
        getLogger().info(e.getInventory().getTitle());
        if (e.getInventory().getTitle().equalsIgnoreCase("Crafting")) {
            new InventoryUpdateRunnable(e.getWhoClicked(), e.getInventory()).runTaskLater(this, 2);
                /*ItemStack[] items = e.getInventory().getContents();
                getLogger().info(" ============== ");
                getLogger().info("2 :" + (items[2] == null ? "null" : items[2].getType().name()));
                getLogger().info("11 :" + (items[11] == null ? "null" : items[11].getType().name()));
                getLogger().info("20 :" + (items[20] == null ? "null" : items[20].getType().name()));
                getLogger().info("28 :" + (items[28] == null ? "null" : items[28].getType().name()));
                getLogger().info("30 :" + (items[30] == null ? "null" : items[30].getType().name()));
                getLogger().info("38 :" + (items[38] == null ? "null" : items[38].getType().name()));
                getLogger().info("29 :" + (items[29] == null ? "null" : items[29].getType().name()));
                getLogger().info("8 :" + (items[8] == null ? "null" : items[8].getType().name()));
                getLogger().info(" ============== ");
                */
        }
        //2,11,20,28,30,38 iron
        //29 eme  ->        //34 sword
        //8 paper ->        //51,52,53 (barrier)
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e) {
        if (opens.containsKey(e.getPlayer())) {
            opens.get(e.getPlayer()).cancel();
            opens.remove(e.getPlayer());
        }
    }
}
