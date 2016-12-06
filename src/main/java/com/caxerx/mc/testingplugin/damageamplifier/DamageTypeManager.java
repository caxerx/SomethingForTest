package com.caxerx.mc.testingplugin.damageamplifier;

import java.util.HashMap;

/**
 * Created by caxerx on 2016/12/6.
 */
public class DamageTypeManager {
    private static HashMap<String, DamageType> registeredType=new HashMap<>();

    public static void registerDamageType(DamageType damageType) {
        String typeName=damageType.getType();
        if (!registeredType.containsKey(typeName)) {
            registeredType.put(typeName, damageType);
        }
    }

}
