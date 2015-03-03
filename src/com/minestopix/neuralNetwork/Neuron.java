package com.minestopix.neuralNetwork;


/**
 * Created by MinestoPix on 3/3/2015.
 */
public abstract class Neuron {

    public abstract void increasePotential(Neuron from, float potential);

    public abstract void increaseStrength(Neuron to, float strength);

    public abstract void activate();

}
