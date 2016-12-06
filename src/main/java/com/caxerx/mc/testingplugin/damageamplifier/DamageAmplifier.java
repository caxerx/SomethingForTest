package com.caxerx.mc.testingplugin.damageamplifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by caxerx on 2016/12/6.
 */
public class DamageAmplifier {
    private List<DamageInfluence> influences;

    public DamageAmplifier(DamageInfluence... influence) {
        influences = Arrays.asList(influence);
    }

    public void apply(Damage damage) {
        HashMap<List<DamageType>, Double> damages = damage.getAllDamage();
        for (List<DamageType> damageType : damages.keySet()) {
            double damageValue = damages.get(damageType);
            double increase = 1;
            List<Double> multi = new ArrayList<>();
            for (DamageInfluence influence : influences) {
                if (damageType.containsAll(influence.getDamageType())) {
                    double value = influence.getValue();
                    switch (influence.getInfluenceType()) {
                        case INCREASE:
                            increase += value;
                            break;
                        case DECREASE:
                            increase -= value;
                            break;
                        case MORE:
                            multi.add(1d + value);
                            break;
                        case LESS:
                            multi.add(1d - value);
                            break;
                        default:
                    }
                }
            }
            damageValue *= increase;
            for (double multiplier : multi) {
                damageValue *= multiplier;
            }
            damages.put(damageType, damageValue);
        }
    }
}
