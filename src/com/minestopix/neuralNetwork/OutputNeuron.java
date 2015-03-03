package com.minestopix.neuralNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinestoPix on 3/3/2015.
 */
public class OutputNeuron extends Neuron {

    public static int idCount = 0;
    private final int id;
    List<Neuron> from = new ArrayList<Neuron>();
    float potential = 0;
    float potentialLimit;
    boolean activated = false;

    public OutputNeuron(float potentialLimit) {
        this.potentialLimit = potentialLimit;
        id = idCount++;
    }

    public OutputNeuron() {
        this(1f);
    }

    @Override
    public void activate() {

        if (potential >= potentialLimit) {
            Main.outputNeutronList.add(this);
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
        this.from.add(from);
        this.potential += potential;
    }

    public void increaseStrength(Neuron to, float strength) {
        if (from.size() > 0) {
            for (Neuron n : from) {
                n.increaseStrength(this, strength);
            }
        }
    }

    public String toString() {
        return potential + " >= " + potentialLimit + " | " + "OutputNeutron with ID " + id;
    }

}
