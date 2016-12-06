package com.caxerx.mc.testingplugin.damageamplifier;

/**
 * Created by caxerx on 2016/12/6.
 */
public class OperationClass {
    public static void main(String[] args) {

        DamageType fire = new DamageType("Fire", 1.5);
        DamageType chaos = new DamageType("Chaos", 4);
        DamageType attack = new DamageType("Attack", 1);
        DamageType spell = new DamageType("Spell", 1);

        DamageTypeManager.registerDamageType(fire);
        DamageTypeManager.registerDamageType(chaos);
        DamageTypeManager.registerDamageType(attack);
        DamageTypeManager.registerDamageType(spell);

        BaseDamage fireAttackDamage = new BaseDamage(4, fire, attack);
        BaseDamage fireSpellDamage = new BaseDamage(4, fire, spell);
        BaseDamage chaosSpellDamage = new BaseDamage(4, chaos, spell);

        Damage damage = new Damage(fireAttackDamage, fireSpellDamage, chaosSpellDamage);

        DamageInfluence moreFireDamage = new DamageInfluence(InfluenceType.MORE, 1, fire);
        DamageInfluence increaseFireDamage = new DamageInfluence(InfluenceType.INCREASE, 0.8, fire);
        DamageInfluence lessFireSpellDamage = new DamageInfluence(InfluenceType.LESS, 0.3, fire, spell);
        DamageInfluence decreaseAttackDamage = new DamageInfluence(InfluenceType.DECREASE, 0.25, attack);

        DamageAmplifier amplifier = new DamageAmplifier(moreFireDamage, decreaseAttackDamage, lessFireSpellDamage, increaseFireDamage);
        amplifier.apply(damage);
        damage.getAllDamage().forEach((damageTypes, aDouble) -> {
            damageTypes.forEach(damageType -> System.out.print("_" + damageType.getType()));
            System.out.print(":");
            System.out.println(aDouble);
        });
    }
}
