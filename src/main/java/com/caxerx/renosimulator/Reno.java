package com.caxerx.renosimulator;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by caxerx on 2016/12/11.
 */
public class Reno {
    public static void main(String args[]) {
        ArrayList<Integer> predeck = new ArrayList<>(Arrays.asList(new Integer[29]));
        Collections.fill(predeck, 0);
        predeck.add(1);
        int success = 0;
        int first = 0;
        List<Integer> taps = Lists.newArrayList();
        for (int b = 0; b < 3000000; b++) {
            List<Integer> deck = (List<Integer>) predeck.clone();
            Collections.shuffle(deck);
            if (Math.random() >= 0.5) {
                if (!(deck.get(0) == 1 || deck.get(1) == 1 || deck.get(2) == 1 || deck.get(3) == 1)) { //起手
                    Collections.shuffle(deck); //shuffle in
                    if (deck.get(0) == 1 || deck.get(1) == 1 || deck.get(2) == 1 || deck.get(3) == 1) {
                        System.out.println("mulligan, change all(" + deck.get(0) + deck.get(1) + deck.get(2) + deck.get(3) + ")");
                        success++;
                        continue;
                    }
                } else {
                    System.out.println("mulligan(" + deck.get(0) + deck.get(1) + deck.get(2) + deck.get(3) + ")");
                    success++;
                    continue;
                }

                deck.remove(0);
                deck.remove(0);
                deck.remove(0);
                deck.remove(0);
            } else {
                first++;
                if (!(deck.get(0) == 1 || deck.get(1) == 1 || deck.get(2) == 1)) {
                    Collections.shuffle(deck);
                    if (deck.get(0) == 1 || deck.get(1) == 1 || deck.get(2) == 1) {
                        System.out.println("mulligan, change all(" + deck.get(0) + deck.get(1) + deck.get(2) + ")");
                        success++;
                        taps.add(0);
                        continue;
                    }
                } else {
                    System.out.println("mulligan(" + deck.get(0) + deck.get(1) + deck.get(2) + ")");
                    success++;
                    taps.add(0);
                    continue;
                }
                deck.remove(0);
                deck.remove(0);
                deck.remove(0);
            }
            int remain = deck.size();
            int i2 = 0;
            List<Integer> tapped = Lists.newArrayList();
            for (int i = 0; i < remain; i++) {
                if (deck.get(0) == 1) {
                    System.out.println("Turn: " + (i2 + 1) + "(Tapped: " + tapped.toString() + ")");
                    if (i2 < 6) {
                        success++;
                        taps.add(tapped.size());
                    }
                    break;
                }
                deck.remove(0);
                if ((i2 != 5) && Math.random() >= (2 / 7)) {
                    i++;
                    if (deck.get(0) == 1) {
                        tapped.add(i2 + 1);
                        System.out.println("<Success> Tap Turn: " + (i2 + 1) + "(Tapped: " + tapped.toString() + ")");
                        if (i2 < 6) {
                            success++;
                            taps.add(tapped.size());
                        }
                        break;
                    } else {
                        tapped.add(i2 + 1);
                    }
                    deck.remove(0);
                }
                i2++;
            }
        }
        System.out.println("先手機率: " + (double) first / 3000000d);
        System.out.println("T6前抽到機率: " + (double) success / 3000000d);
        System.out.println("平均tap次數: " + (double) taps.stream().mapToInt((Integer::intValue)).sum() / (double) taps.size());
    }
}
