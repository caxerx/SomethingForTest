package com.caxerx.mc.testingplugin.damageamplifier;

import java.util.Arrays;
import java.util.List;

/**
 * Created by caxerx on 2016/12/6.
 */
public class BaseDamage {
    List<DamageType> damageType;
    double value;

    public BaseDamage(double value, DamageType... type) {
        damageType = Arrays.asList(type);
        damageType.sort(new DamageTypeComparator());
        this.value = value;
    }

    public List<DamageType> getDamageType() {
        return damageType;
    }

    public double getDamage() {
        return value;
    }

}
