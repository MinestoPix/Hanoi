package com.minestopix.neuralNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinestoPix on 3/3/2015.
 */
public class OutputNeutron extends Neutron {

    public static int idCount = 0;
    private final int id;
    List<Neutron> from = new ArrayList<Neutron>();
    float potential = 0;
    float potentialLimit;
    boolean activated = false;

    public OutputNeutron(float potentialLimit) {
        this.potentialLimit = potentialLimit;
        id = idCount++;
    }

    public OutputNeutron() {
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
    public void increasePotential(Neutron from, float potential) {
        if (activated) {
            this.from.clear();
            activated = false;
        }
        this.from.add(from);
        this.potential += potential;
    }

    public void increaseStrength(Neutron to, float strength) {
        if (from.size() > 0) {
            for (Neutron n : from) {
                n.increaseStrength(this, strength);
            }
        }
    }

    public String toString() {
        return potential + " >= " + potentialLimit + " | " + "OutputNeutron with ID " + id;
    }

}
