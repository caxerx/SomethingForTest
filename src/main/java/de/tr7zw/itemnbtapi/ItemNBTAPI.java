package de.tr7zw.itemnbtapi;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class ItemNBTAPI {

    private static boolean compatible = false;

    public ItemNBTAPI(Plugin plugin) {
        plugin.getLogger().info("Running NBT reflection test...");
        try {
            ItemStack item = new ItemStack(Material.STONE);
            NBTItem nbtItem = new NBTItem(item);

            nbtItem.setString("stringTest", "C8763");
            nbtItem.setInteger("intTest", 48763);
            nbtItem.setDouble("doubleTest", 487.63d);
            nbtItem.setBoolean("booleanTest", true);

            item = nbtItem.getItem();
            nbtItem = new NBTItem(item);

            if (!nbtItem.hasKey("stringTest")) {
                plugin.getLogger().info("Does not have key...");
                return;
            }
            if (!nbtItem.getString("stringTest").equals("C8763")
                    || nbtItem.getInteger("intTest") != 48763
                    || nbtItem.getDouble("doubleTest") != 487.63d
                    || !nbtItem.getBoolean("booleanTest")) {
                plugin.getLogger().info("Key does not equal original value...");

                return;
            }
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, null, ex);
            return;
        }

        compatible = true;
        plugin.getLogger().info("Success! This version of Item-NBT-API is compatible with your server.");
    }

    public boolean isCompatible() {
        return compatible;
    }


}
