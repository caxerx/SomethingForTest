package com.caxerx.mc.testingplugin.damageamplifier;

import java.util.Arrays;
import java.util.List;

/**
 * Created by caxerx on 2016/12/6.
 */
public class DamageInfluence {
    private List<DamageType> types;
    private InfluenceType itype;
    private double value;

    public DamageInfluence(InfluenceType influenceType, double value, DamageType... damageTypes) {
        types = Arrays.asList(damageTypes);
        types.sort(new DamageTypeComparator());
        itype = influenceType;
        this.value = value;
    }

    public List<DamageType> getDamageType() {
        return types;
    }

    public InfluenceType getInfluenceType() {
        return itype;
    }

    public double getValue() {
        return value;
    }
}
