package com.caxerx.mc.testingplugin.damageamplifier;

/**
 * Created by caxerx on 2016/12/6.
 */
public class DamageType {
    private double damageAmplifier;
    private String typeName;

    public DamageType(String typeName, double damageAmplifier) {
        this.damageAmplifier = damageAmplifier;
        this.typeName = typeName;
    }

    public String getType() {
        return typeName;
    }

    public double getDamageAmplifier() {
        return damageAmplifier;
    }

    public boolean equals(DamageType type) {
        return damageAmplifier == type.getDamageAmplifier() && typeName == type.getType();
    }

}
