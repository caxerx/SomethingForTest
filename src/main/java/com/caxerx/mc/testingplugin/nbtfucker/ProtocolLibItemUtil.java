package com.caxerx.mc.testingplugin.nbtfucker;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.io.NbtTextSerializer;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caxerx on 2016/8/18.
 */
public class ProtocolLibItemUtil {

    public static Map<String, Object> serialize(ItemStack item) {
        HashMap<String, Object> itemDocument = new HashMap(item.serialize());
        if (item.hasItemMeta()) {
            NbtCompound nbtTag = NbtFactory.asCompound(NbtFactory.fromItemTag(item));
            itemDocument.put("meta", new NbtTextSerializer().serialize(nbtTag));
        }
        return itemDocument;
    }

    public static ItemStack deserialize(Map<String, Object> map) {
        ItemStack i = ItemStack.deserialize(map);
        if (map.containsKey("meta")) {
            try {
                NbtCompound nbtComp = NbtFactory.asCompound(new NbtTextSerializer().deserialize((String) map.get("meta")));
                i = MinecraftReflection.getBukkitItemStack(i);
                NbtFactory.setItemTag(i, nbtComp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return i;
    }
}
