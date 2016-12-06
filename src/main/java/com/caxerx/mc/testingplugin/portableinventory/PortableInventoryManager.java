package com.caxerx.mc.testingplugin.portableinventory;

import com.caxerx.mc.testingplugin.portableinventory.library.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;

/**
 * Created by caxerx on 2016/8/17.
 */

//45 - 53 page indicator

public class PortableInventoryManager {
    private ItemStack previousPageItem, nextPageItem, pagePlaceholderItem;
    private HashMap<Player, PlayerPortableInventory> onlinePlayerInventory;
    private Plugin plugin;

    public PortableInventoryManager(Plugin plugin) {
        this.plugin = plugin;
        onlinePlayerInventory = new HashMap<>();
        ItemStack tempStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta tempMeta = tempStack.getItemMeta();
        tempMeta.setDisplayName(ChatColor.WHITE + "上一頁");
        tempStack.setItemMeta(tempMeta);
        NBTItem tempItem = new NBTItem(tempStack);
        tempItem.setBoolean("PortableInventoryItem", true);
        tempItem.setString("indicator", "previousPage");
        previousPageItem = tempItem.getItem();

        tempStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        tempMeta = tempStack.getItemMeta();
        tempMeta.setDisplayName(ChatColor.WHITE + "下一頁");
        tempItem.setBoolean("PortableInventoryItem", true);
        tempStack.setItemMeta(tempMeta);
        tempItem = new NBTItem(tempStack);
        tempItem.setBoolean("PortableInventoryItem", true);
        tempItem.setString("indicator", "nextPage");
        nextPageItem = tempItem.getItem();

        tempStack = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        tempMeta = tempStack.getItemMeta();
        tempMeta.setDisplayName(ChatColor.DARK_GRAY + "== N/A ==");
        tempItem.setBoolean("PortableInventoryItem", true);
        tempStack.setItemMeta(tempMeta);
        tempItem = new NBTItem(tempStack);
        tempItem.setBoolean("PortableInventoryItem", true);
        pagePlaceholderItem = tempItem.getItem();

    }

    public PlayerPortableInventory getPlayerInventory(Player player) {
        if (onlinePlayerInventory.containsKey(player)) {
            return onlinePlayerInventory.get(player);
        } else {
            PlayerPortableInventory playerInventory = new PlayerPortableInventory(plugin, this, player);
            onlinePlayerInventory.put(player, playerInventory);
            return playerInventory;
        }
    }

    public void destroyPlayerInventory(Player player) {
        if (onlinePlayerInventory.containsKey(player)) {
            onlinePlayerInventory.remove(player);
        }
    }

    public Inventory generatorPage(List<ItemStack> itemContent, int page, int maxPage) {
        Inventory tempInventory = plugin.getServer().createInventory(null, 54, "第" + page + "頁，共" + maxPage + "頁");
        tempInventory.setContents(itemContent.toArray(new ItemStack[45]));
        for (int i = 0; i < 9; i++) {
            if (i == 0) {
                if (page == 1) {
                    tempInventory.setItem(45 + i, pagePlaceholderItem);
                } else {
                    ItemStack tempItem = previousPageItem.clone();
                    tempItem.setAmount(page - 1);
                    tempInventory.setItem(45 + i, tempItem);
                }
            } else if (i == 8) {
                if (page == maxPage) {
                    tempInventory.setItem(45 + i, pagePlaceholderItem);
                } else {
                    ItemStack tempItem = nextPageItem.clone();
                    tempItem.setAmount(page + 1);
                    tempInventory.setItem(45 + i, tempItem);
                }
            } else {
                tempInventory.setItem(45 + i, pagePlaceholderItem);
            }
        }
        return tempInventory;
    }


    public ItemStack getNextPageItem() {
        return nextPageItem;
    }

    public ItemStack getPreviousPageItem() {
        return previousPageItem;
    }

    public ItemStack getPagePlaceholderItem() {
        return pagePlaceholderItem;
    }
}
