package com.caxerx.mc.testingplugin.portableinventory.library;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {

    @SuppressWarnings("rawtypes")
    private static Class getCraftItemStack() {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            Class c = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
            //Constructor<?> cons = c.getConstructor(ItemStack.class);
            //return cons.newInstance(item);
            return c;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    private static Object getNewNBTTag() {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("net.minecraft.server." + version + ".NBTTagCompound");
            return c.newInstance();
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    private static Object setNBTTag(Object NBTTag, Object NMSItem) {
        try {
            java.lang.reflect.Method method;
            method = NMSItem.getClass().getMethod("setTag", NBTTag.getClass());
            method.invoke(NMSItem, NBTTag);
            return NMSItem;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static Object getNMSItemStack(ItemStack item) {
        @SuppressWarnings("rawtypes")
        Class cis = getCraftItemStack();
        java.lang.reflect.Method method;
        try {
            method = cis.getMethod("asNMSCopy", ItemStack.class);
            Object answer = method.invoke(cis, item);
            return answer;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    private static ItemStack getBukkitItemStack(Object item) {
        @SuppressWarnings("rawtypes")
        Class cis = getCraftItemStack();
        java.lang.reflect.Method method;
        try {
            method = cis.getMethod("asCraftMirror", item.getClass());
            Object answer = method.invoke(cis, item);
            return (ItemStack) answer;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    private static Object getNBTTagCompound(Object nmsitem) {
        @SuppressWarnings("rawtypes")
        Class c = nmsitem.getClass();
        java.lang.reflect.Method method;
        try {
            method = c.getMethod("getTag");
            Object answer = method.invoke(nmsitem);
            return answer;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static ItemStack setString(ItemStack item, String key, String Text) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object nbttag = getNBTTagCompound(nmsitem);
        if (nbttag == null) {
            nbttag = getNewNBTTag();
        }
        java.lang.reflect.Method method;
        try {
            method = nbttag.getClass().getMethod("setString", String.class, String.class);
            method.invoke(nbttag, key, Text);
            nmsitem = setNBTTag(nbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public static String getString(ItemStack item, String key) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object nbttag = getNBTTagCompound(nmsitem);
        if (nbttag == null) {
            nbttag = getNewNBTTag();
        }
        java.lang.reflect.Method method;
        try {
            method = nbttag.getClass().getMethod("getString", String.class);
            return (String) method.invoke(nbttag, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setInt(ItemStack item, String key, Integer i) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object nbttag = getNBTTagCompound(nmsitem);
        if (nbttag == null) {
            nbttag = getNewNBTTag();
        }
        java.lang.reflect.Method method;
        try {
            method = nbttag.getClass().getMethod("setInt", String.class, int.class);
            method.invoke(nbttag, key, i);
            nmsitem = setNBTTag(nbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public static Integer getInt(ItemStack item, String key) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object nbttag = getNBTTagCompound(nmsitem);
        if (nbttag == null) {
            nbttag = getNewNBTTag();
        }
        java.lang.reflect.Method method;
        try {
            method = nbttag.getClass().getMethod("getInt", String.class);
            return (Integer) method.invoke(nbttag, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setDouble(ItemStack item, String key, Double d) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object nbttag = getNBTTagCompound(nmsitem);
        if (nbttag == null) {
            nbttag = getNewNBTTag();
        }
        java.lang.reflect.Method method;
        try {
            method = nbttag.getClass().getMethod("setDouble", String.class, double.class);
            method.invoke(nbttag, key, d);
            nmsitem = setNBTTag(nbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public static Double getDouble(ItemStack item, String key) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object nbttag = getNBTTagCompound(nmsitem);
        if (nbttag == null) {
            nbttag = getNewNBTTag();
        }
        java.lang.reflect.Method method;
        try {
            method = nbttag.getClass().getMethod("getDouble", String.class);
            return (Double) method.invoke(nbttag, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setBoolean(ItemStack item, String key, Boolean d) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object nbttag = getNBTTagCompound(nmsitem);
        if (nbttag == null) {
            nbttag = getNewNBTTag();
        }
        java.lang.reflect.Method method;
        try {
            method = nbttag.getClass().getMethod("setBoolean", String.class, boolean.class);
            method.invoke(nbttag, key, d);
            nmsitem = setNBTTag(nbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public static Boolean getBoolean(ItemStack item, String key) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object nbttag = getNBTTagCompound(nmsitem);
        if (nbttag == null) {
            nbttag = getNewNBTTag();
        }
        java.lang.reflect.Method method;
        try {
            method = nbttag.getClass().getMethod("getBoolean", String.class);
            return (Boolean) method.invoke(nbttag, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Boolean hasKey(ItemStack item, String key) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object nbttag = getNBTTagCompound(nmsitem);
        if (nbttag == null) {
            nbttag = getNewNBTTag();
        }
        java.lang.reflect.Method method;
        try {
            method = nbttag.getClass().getMethod("hasKey", String.class);
            return (Boolean) method.invoke(nbttag, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean isCompatiable() {
        System.out.print("Running NBT reflection test...");
        try {
            ItemStack item = new ItemStack(Material.STONE, 1);
            NBTItem nbtItem = new NBTItem(item);

            nbtItem.setString("stringTest", "TestString");
            nbtItem.setInteger("intTest", 42);
            nbtItem.setDouble("doubleTest", 1.5d);
            nbtItem.setBoolean("booleanTest", true);

            item = nbtItem.getItem();

            if (!nbtItem.hasKey("stringTest")) {
                System.out.print("Does not have key...");
                return false;
            }
            if (!nbtItem.getString("stringTest").equals("TestString")
                    || nbtItem.getInteger("intTest") != 42
                    || nbtItem.getDouble("doubleTest") != 1.5d
                    || !nbtItem.getBoolean("booleanTest")) {
                System.out.print("Key does not equal original value...");
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        System.out.print("Success! This version of Item-NBT-API is compatible with your server.");
        return true;
    }
}