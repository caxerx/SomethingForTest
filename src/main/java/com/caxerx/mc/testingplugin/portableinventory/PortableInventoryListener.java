package com.caxerx.mc.testingplugin.portableinventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Created by caxerx on 2016/8/17.
 */
public class PortableInventoryListener implements Listener {
    Plugin plugin;
    PortableInventoryManager manager;

    public PortableInventoryListener(Plugin plugin, PortableInventoryManager manager) {
        this.manager = manager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        manager.getPlayerInventory(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        manager.destroyPlayerInventory(e.getPlayer());
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        PlayerPortableInventory inventory = manager.getPlayerInventory(player);
        if (e.getInventory().contains(manager.getPagePlaceholderItem())) {
            if (!inventory.isPageChange()) {
                PortableInventoryCloseEvent callback = new PortableInventoryCloseEvent(player, e.getInventory(), inventory.getLastOpenedPage());
                plugin.getServer().getPluginManager().callEvent(callback);
                if (callback.isCancelled()) {
                    player.openInventory(e.getInventory());
                } else {
                    inventory.savePage(e.getInventory(), inventory.getLastOpenedPage());
                }
            } else {
                inventory.togglePageChange();
            }
        }
    }

    @EventHandler
    public void onPlayerClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            ItemStack clickedItem = e.getCurrentItem().clone();
            if (clickedItem.isSimilar(manager.getPreviousPageItem()) || clickedItem.isSimilar(manager.getNextPageItem())) {
                e.setCancelled(true);
                PlayerPortableInventory inventory = manager.getPlayerInventory(player);
                int lastPageOpened = inventory.getLastOpenedPage();
                PortableInventoryPageChangingEvent callback =
                        new PortableInventoryPageChangingEvent(player, e.getInventory(), inventory.getLastOpenedPage(), clickedItem.isSimilar(manager.getPreviousPageItem()) ? lastPageOpened - 1 : lastPageOpened + 1);
                plugin.getServer().getPluginManager().callEvent(callback);
                if (!callback.isCancelled()) {
                    inventory.savePage(e.getInventory(), callback.getPreviousPage());
                    inventory.togglePageChange();
                    inventory.open(callback.getNextPage());
                }
            } else if (clickedItem.isSimilar(manager.getPagePlaceholderItem())) {
                e.setCancelled(true);
            }
        }
    }
}
