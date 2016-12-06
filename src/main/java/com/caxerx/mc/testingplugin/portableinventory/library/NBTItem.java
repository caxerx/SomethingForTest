package com.caxerx.mc.testingplugin.portableinventory.library;

import org.bukkit.inventory.ItemStack;

public class NBTItem {

    private ItemStack bukkitItem;
    
    public NBTItem(ItemStack item) {
        bukkitItem = item.clone();
    }

    public ItemStack getItem() {
        return bukkitItem;
    }

    public void setString(String key, String value) {
        bukkitItem = NBTUtil.setString(bukkitItem, key, value);
    }

    public String getString(String key) {
        return NBTUtil.getString(bukkitItem, key);
    }

    public void setInteger(String key, int value) {
        bukkitItem = NBTUtil.setInt(bukkitItem, key, value);
    }

    public Integer getInteger(String key) {
        return NBTUtil.getInt(bukkitItem, key);
    }

    public void setDouble(String key, double value) {
        bukkitItem = NBTUtil.setDouble(bukkitItem, key, value);
    }

    public double getDouble(String key) {
        return NBTUtil.getDouble(bukkitItem, key);
    }

    public void setBoolean(String key, boolean value) {
        bukkitItem = NBTUtil.setBoolean(bukkitItem, key, value);
    }

    public boolean getBoolean(String key) {
        return NBTUtil.getBoolean(bukkitItem, key);
    }

    public boolean hasKey(String key) {
        return NBTUtil.hasKey(bukkitItem, key);
    }

}