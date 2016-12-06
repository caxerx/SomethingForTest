package com.caxerx.mc.testingplugin.damageamplifier;

import java.util.HashMap;
import java.util.List;

/**
 * Created by caxerx on 2016/12/6.
 */
public class Damage {
    private HashMap<List<DamageType>, Double> baseDamages;

    public Damage(BaseDamage... baseDamages) {
        setupDamage(baseDamages);
    }

    /*
    public void setDamage(BaseDamage[] damages) {
        //baseDamages = damages;
    }
*/
    public HashMap<List<DamageType>, Double> getAllDamage() {
        return baseDamages;
    }

    public void setDamage(BaseDamage baseDamage) {
        List<DamageType> baseType = baseDamage.getDamageType();
        if (this.baseDamages.containsKey(baseType)) {
            double damage = this.baseDamages.get(baseType);
            damage += baseDamage.getDamage();
            this.baseDamages.put(baseType, damage);
        } else {
            this.baseDamages.put(baseDamage.getDamageType(), baseDamage.getDamage());
        }
    }

    private void setupDamage(BaseDamage[] baseDamages) {
        this.baseDamages = new HashMap<>();
        for (BaseDamage base : baseDamages) {
            setDamage(base);
        }
    }
}
