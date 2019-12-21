package com.teamcitrus.fimbulwinter.common.objects.items;

import java.util.ArrayList;

public class HitChain {

    private final ArrayList<Double> hitDamage;

    public HitChain(ArrayList<Double> hitDamage) {
        this.hitDamage = hitDamage;

    }

    public ArrayList<Double> getHitDamage() {
        return hitDamage;
    }

    public int getChainSize() {
        return hitDamage.size() - 1;
    }

}
