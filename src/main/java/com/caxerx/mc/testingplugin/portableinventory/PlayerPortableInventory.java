package com.caxerx.mc.testingplugin.portableinventory;

import com.caxerx.mc.testingplugin.portableinventory.library.NBTItem;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by caxerx on 2016/8/17.
 */
public class PlayerPortableInventory {
    private FileConfiguration fileConfiguration;
    private File inventoryFile;
    private final Player player;
    private int lastOpenedPage;
    private int maxPage;
    boolean pageChangeTrigger = false;
    private Plugin plugin;
    private PortableInventoryManager manager;
    private Inventory lastOpenedInventory;

    public PlayerPortableInventory(Plugin plugin, PortableInventoryManager manager, Player player) {
        this.player = player;
        this.plugin = plugin;
        this.manager = manager;
        lastOpenedPage = -1;
        fileConfiguration = new YamlConfiguration();
        try {
            inventoryFile = new File(plugin.getDataFolder() + File.separator + "inventory" + File.separator + player.getUniqueId() + ".yml");
            File defaultInventorySetting = new File(plugin.getDataFolder() + File.separator + "DefaultInventorySetting.yml");
            if (!defaultInventorySetting.exists()) {
                FileUtils.copyInputStreamToFile(plugin.getResource("DefaultInventorySetting.yml"), defaultInventorySetting);
            }
            YamlConfiguration defaultConfig = new YamlConfiguration();
            defaultConfig.load(defaultInventorySetting);
            fileConfiguration.setDefaults(defaultConfig);
            fileConfiguration.options().copyDefaults(true);
            if (!inventoryFile.exists()) {
                fileConfiguration.save(inventoryFile);
            }
            fileConfiguration.load(inventoryFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "IOException while loading " + player.getName() + "'s Portable Inventory", e);
        } catch (InvalidConfigurationException e) {
            plugin.getLogger().log(Level.SEVERE, "Config malformation at " + player.getName() + "'s Portable Inventory file", e);
        }
        maxPage = fileConfiguration.getInt("maxPage");
    }

    public Inventory getInventoryPage(int page) {
        return manager.generatorPage(getPageContent(page), page, maxPage);
    }

    public boolean isPageChange() {
        return pageChangeTrigger;
    }

    public void savePage(Inventory inventory, int page) {
        List<ItemStack> itemsInside = Lists.newArrayList(inventory.getContents());
        itemsInside.removeIf(itemStack -> itemStack != null && new NBTItem(itemStack).hasKey("PortableInventoryItem"));
        List<Map<String, Object>> preConvertGson = new ArrayList<>();
        itemsInside.forEach(itemStack -> {
            preConvertGson.add(itemStack == null ? null : itemStack.serialize());
        });
        fileConfiguration.set("inventoryContent." + page, new Gson().toJson(preConvertGson));
        try {
            fileConfiguration.save(inventoryFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "IOException when saving " + player.getName() + "'s Inventory", e);
        }
    }

    public List<ItemStack> getPageContent(int page) {
        if (page > maxPage) {
            plugin.getLogger().warning(player.getName() + "trying request exceed maximum inventory");
        }
        String jsonString = fileConfiguration.contains("inventoryContent." + page) ? fileConfiguration.getString("inventoryContent." + page) : "[]";
        Class inventoryFormatType = new ArrayList<Map<String, Object>>().getClass();
        ArrayList<Map<String, Object>> lists = (ArrayList<Map<String, Object>>) new Gson().fromJson(jsonString, inventoryFormatType);
        List<ItemStack> itemList = new ArrayList<>();
        lists.forEach(itemStack -> itemList.add(itemStack != null ? ItemStack.deserialize(itemStack) : null));
        return itemList;
    }

    public void togglePageChange() {
        pageChangeTrigger = !pageChangeTrigger;
    }

    public int getLastOpenedPage() {
        return lastOpenedPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void open(int page) {
        lastOpenedPage = page;
        lastOpenedInventory=getInventoryPage(page);
        player.openInventory(lastOpenedInventory);
    }

    public Inventory getLastOpenedInventory() {
        return lastOpenedInventory;
    }
}
