package com.caxerx.mc.testingplugin.portableinventory;

import com.caxerx.mc.testingplugin.portableinventory.library.NBTUtil;
import org.apache.commons.io.FileUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by caxerx on 2016/8/17.
 */
public class PortableInventory extends JavaPlugin {
    PortableInventoryManager manager;
    PortableInventoryAPI api;


    public void onEnable() {
        File inventoryFolder = new File(getDataFolder() + File.separator + "inventory" + File.separator);
        if (!inventoryFolder.exists()) {
            inventoryFolder.mkdir();
        }
        File defaultInventorySetting = new File(getDataFolder() + File.separator + "DefaultInventorySetting.yml");
        if (!defaultInventorySetting.exists()) {
            try {
                FileUtils.copyInputStreamToFile(getResource("DefaultInventorySetting.yml"), defaultInventorySetting);
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "IOException when coping DefaultInventorySetting", e);
            }
        }
        if (!NBTUtil.isCompatiable()) {
            getServer().getPluginManager().disablePlugin(this);
        }
        manager = new PortableInventoryManager(this);
        api = new PortableInventoryAPI(this, manager);
        getServer().getPluginManager().registerEvents(new PortableInventoryListener(this, manager), this);
    }

    public void onDisable() {
        getServer().getOnlinePlayers().forEach(player -> {
            player.closeInventory();
            PlayerPortableInventory portableInventory = manager.getPlayerInventory(player);
            portableInventory.savePage(portableInventory.getLastOpenedInventory(), portableInventory.getLastOpenedPage());
            manager.destroyPlayerInventory(player);
        });
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("test")) { // If the player typed /basic then do the following...
            if (sender instanceof Player) {
                Player player = (Player) sender;
                PortableInventoryAPI.openPortableInventory(player, 1);
            }
            return true;
        }
        return false;
    }

}
