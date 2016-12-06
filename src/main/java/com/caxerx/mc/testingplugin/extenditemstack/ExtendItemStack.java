package com.caxerx.mc.testingplugin.extenditemstack;

import de.tr7zw.itemnbtapi.ItemNBTAPI;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by caxerx on 2016/8/12.
 */
public class ExtendItemStack extends JavaPlugin implements Listener {
    public void onEnable() {
        ItemNBTAPI api = new ItemNBTAPI(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) {
            NBTItem item = new NBTItem(e.getPlayer().getInventory().getItemInMainHand());
            if (item.hasKey("Extended") && item.getBoolean("Extended")) {
                e.getPlayer().chat("WOW, Legendary!");
                e.getPlayer().sendRawMessage("==============x[Start]x===========");
                item.getItem().serialize().forEach((a, b) -> {
                    if (!a.equalsIgnoreCase("meta")) {
                        e.getPlayer().sendRawMessage("Key: " + a);
                        e.getPlayer().sendRawMessage("Value: " + b);
                    }
                });
                if (item.getItem().hasItemMeta()) {
                    item.getItem().getItemMeta().serialize().forEach((a, b) -> {
                        e.getPlayer().sendRawMessage("Key: " + a);
                        e.getPlayer().sendRawMessage("Value: " + b);
                    });
                }
                e.getPlayer().sendRawMessage("==============x[Ended]x===========");
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (!e.getMessage().equalsIgnoreCase("WOW, Legendary!")) {

            if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) {
                NBTItem item = new NBTItem(new ItemStack(e.getPlayer().getInventory().getItemInMainHand()));
                item.setBoolean("Extended", true);
                e.getPlayer().getInventory().addItem(item.getItem());
            }

        }
    }
}
