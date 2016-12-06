package com.caxerx.mc.testingplugin.damageamplifier;

import java.util.Comparator;

/**
 * Created by caxerx on 2016/12/7.
 */
public class DamageTypeComparator implements Comparator<DamageType> {
    @Override
    public int compare(DamageType o1, DamageType o2) {
        int typeComp = o1.getType().compareTo(o2.getType());
        return typeComp != 0 ? typeComp : (int) (o1.getDamageAmplifier() * 10 - o2.getDamageAmplifier() * 10);
    }
}
