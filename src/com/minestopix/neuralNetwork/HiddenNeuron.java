package com.minestopix.neuralNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MinestoPix on 3/3/2015.
 */
public class HiddenNeuron extends Neuron {

    public static int idCount = 0;
    private final int id;
    List<Neuron> from = new ArrayList<Neuron>();
    List<Neuron> to;
    float[] signalStr;
    float potential = 0;
    float potentialLimit;
    boolean activated = false;

    public HiddenNeuron(List<Neuron> to, float potentialLimit, float defStr) {

        this.to = to;
        signalStr = new float[to.size()];
        for (int i = 0; i < signalStr.length; i++) {
            signalStr[i] = new Random().nextInt(10);
        }
        this.potentialLimit = potentialLimit;
        id = idCount++;

    }

    public HiddenNeuron(List<Neuron> to, float potentialLimit) {

        this(to, potentialLimit, 1f);

    }

    public HiddenNeuron(List<Neuron> to) {

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
    public void increasePotential(Neuron from, float potential) {
        if (activated) {
            this.from.clear();
            activated = false;
        }
        this.potential += potential;
        this.from.add(from);
    }

    public void increaseStrength(Neuron to, float strength) {
        if (from.size() > 0) {
            for (Neuron n : from) {
                n.increaseStrength(this, strength);
            }
        }
        signalStr[this.to.indexOf(to)] += strength;
    }

    public String toString() {
        return "HiddenNeutron with ID " + id;
    }

}
