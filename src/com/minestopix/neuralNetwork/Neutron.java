package com.minestopix.neuralNetwork;


/**
 * Created by MinestoPix on 3/3/2015.
 */
public abstract class Neutron {

    public abstract void increasePotential(Neutron from, float potential);

    public abstract void increaseStrength(Neutron to, float strength);

    public abstract void activate();

}
