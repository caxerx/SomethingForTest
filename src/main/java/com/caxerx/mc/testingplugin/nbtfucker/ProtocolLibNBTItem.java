package com.caxerx.mc.testingplugin.nbtfucker;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.inventory.ItemStack;

public class ProtocolLibNBTItem {

    private ItemStack craftItem;
    private NbtCompound nbtCompound;

    public ProtocolLibNBTItem(ItemStack item) {
        craftItem = MinecraftReflection.getBukkitItemStack(item.clone());
        nbtCompound = NbtFactory.asCompound(NbtFactory.fromItemTag(craftItem));
    }

    public ItemStack getItem() {
        NbtFactory.setItemTag(craftItem, nbtCompound);
        return craftItem;
    }

    public void setString(String key, String value) {
        nbtCompound.put(key, value);
    }

    public String getString(String key) {
        return nbtCompound.getString(key);
    }

    public void setInteger(String key, int value) {
        nbtCompound.put(key, value);
    }

    public Integer getInteger(String key) {
        return nbtCompound.getInteger(key);
    }

    public void setDouble(String key, double value) {
        nbtCompound.put(key, value);
    }

    public double getDouble(String key) {
        return nbtCompound.getDouble(key);
    }

    public boolean hasKey(String key) {
        return nbtCompound.containsKey(key);
    }

}