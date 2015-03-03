package com.minestopix.neuralNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinestoPix on 3/3/2015.
 */
public class HiddenNeutron extends Neutron {

    public static int idCount = 0;
    private final int id;
    List<Neutron> from = new ArrayList<Neutron>();
    List<Neutron> to;
    float[] signalStr;
    float potential = 0;
    float potentialLimit;
    boolean activated = false;

    public HiddenNeutron(List<Neutron> to, float potentialLimit, float defStr) {

        this.to = to;
        signalStr = new float[to.size()];
        for (int i = 0; i < signalStr.length; i++) {
            signalStr[i] = defStr;
        }
        this.potentialLimit = potentialLimit;
        id = idCount++;

    }

    public HiddenNeutron(List<Neutron> to, float potentialLimit) {

        this(to, potentialLimit, 0.5f);

    }

    public HiddenNeutron(List<Neutron> to) {

        this(to, 1);

    }

    @Override
    public void activate() {
        if (potential >= potentialLimit) {
            for (int i = 0; i < to.size(); i++) {
                to.get(i).increasePotential(this, signalStr[i]);
            }
        }
        potential = 0;
        activated = true;
    }

    @Override
    public void increasePotential(Neutron from, float potential) {
        if (activated) {
            this.from.clear();
            activated = false;
        }
        this.potential += potential;
        this.from.add(from);
    }

    public void increaseStrength(Neutron to, float strength) {
        if (from.size() > 0) {
            for (Neutron n : from) {
                n.increaseStrength(this, strength);
            }
        }
        signalStr[this.to.indexOf(to)] += strength;
    }

    public String toString() {
        return "HiddenNeutron with ID " + id;
    }

}
