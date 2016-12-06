package com.caxerx.mc.testingplugin.looplore;

import java.util.ArrayList;

/**
 * Created by caxerx on 2016/9/2.
 */
public class LoreLoooper {
    public static void main(String[] args) {
        ArrayList<String> lores = new ArrayList<>();
        lores.add("+80 遠程");

        lores.forEach(lore -> {
            int a = lore.indexOf(" 遠程");
            int b = lore.indexOf("+");
            System.out.println(lore.substring(b+1,a));
        });

    }
}
