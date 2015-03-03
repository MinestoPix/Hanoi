package com.minestopix.neuralNetwork;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by MinestoPix on 3/3/2015.
 */
public class Main {

    public static List<OutputNeuron> outputNeutronList = new ArrayList<OutputNeuron>();

    public static void main(String[] args) {

        List<Neuron> outputNeurons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            outputNeurons.add(new OutputNeuron());
        }


        List<Neuron> hiddenNeurons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            hiddenNeurons.add(new HiddenNeuron(outputNeurons));
        }

        List<Neuron> hiddenNeutrons1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            hiddenNeutrons1.add(new HiddenNeuron(hiddenNeurons));
        }

        List<Neuron> hiddenNeutrons2 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            hiddenNeutrons2.add(new HiddenNeuron(hiddenNeutrons1));
        }

        List<Neuron> hiddenNeutrons3 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            hiddenNeutrons3.add(new HiddenNeuron(hiddenNeutrons1));
        }


        HiddenNeuron inputNeutron1 = new HiddenNeuron(hiddenNeutrons2, 0);
        HiddenNeuron inputNeutron2 = new HiddenNeuron(hiddenNeutrons2, 0);
        HiddenNeuron inputNeutron3 = new HiddenNeuron(hiddenNeutrons2, 0);




        BitSet input;
        BitSet output = new BitSet(3);
        int desOut = 7;
        input = BitSet.valueOf(new long[]{desOut});
        BitSet desired = BitSet.valueOf(new long[]{desOut});


        for (int i = 0; i < 3; i++) {

            output.set(i, outputNeutronList.contains(outputNeurons.get(i)));

        }


        if (input.get(0))
            inputNeutron1.activate();
        if (input.get(1))
            inputNeutron2.activate();
        if (input.get(2))
            inputNeutron3.activate();


        for (Neuron n : hiddenNeurons) {
            n.activate();
        }
        for (Neuron n : hiddenNeutrons1) {
            n.activate();
        }
        for (Neuron n : hiddenNeutrons2) {
            n.activate();
        }
        for (Neuron n : hiddenNeutrons3) {
            n.activate();
        }
        for (Neuron n : outputNeurons) {
            n.activate();
        }

        int iters = 0;

        while (!(output.equals(desired))) {

            for (int i = 0; i < 3; i++) {
                if (!output.get(i) && desired.get(i)) {
                    outputNeurons.get(i).increaseStrength(null, 0.1f);
                }
                else if (output.get(i) && !desired.get(i)) {
                    outputNeurons.get(i).increaseStrength(null, -0.1f);
                }
            }
//            outputNeutrons.get(0).increaseStrength(null, -0.1f);
//            outputNeutrons.get(1).increaseStrength(null, 0.05f);
//            outputNeutrons.get(2).increaseStrength(null, 0.05f);

//            outputNeutrons.get(0).increaseStrength(null, -0.05f);
//            outputNeutrons.get(1).increaseStrength(null, -0.05f);
//            outputNeutrons.get(2).increaseStrength(null, 0.1f);

//            outputNeutrons.get(0).increaseStrength(null, -0.1f);
//            outputNeutrons.get(1).increaseStrength(null, 0.05f);
//            outputNeutrons.get(2).increaseStrength(null, 0.05f);

            outputNeutronList.clear();

            if (input.get(0)) {
                inputNeutron1.activate();
            }
            if (input.get(1)) {
                inputNeutron2.activate();
            }
            if (input.get(2)) {
                inputNeutron3.activate();
            }

            for (Neuron n : hiddenNeurons) {
                n.activate();
            }
            for (Neuron n : hiddenNeutrons1) {
                n.activate();
            }
            for (Neuron n : hiddenNeutrons2) {
                n.activate();
            }
            for (Neuron n : hiddenNeutrons3) {
                n.activate();
            }
            for (Neuron n : outputNeurons) {
                n.activate();
            }

            for (int i = 0; i < 3; i++) {

                output.set(i, outputNeutronList.contains(outputNeurons.get(i)));

            }
            int value = 0;
            for (int i = output.nextSetBit(0); i >= 0; i = output.nextSetBit(i + 1)) {
                value += (1 << i);
            }
//            System.out.println(value);
            iters++;
            if ((output.equals(desired))) {
                if(iters > 1){
                    System.out.println(iters + " iterations");
                }
                iters = 0;
                if (desOut < 7) {
                    desOut++;
//                    System.out.println("Desired result changed to " + desOut);
                    desired = BitSet.valueOf(new long[]{desOut});
                    input = BitSet.valueOf(new long[]{desOut});
                }else{
                    desOut = 0;
//                    System.out.println("Desired result changed to " + desOut);
                    desired = BitSet.valueOf(new long[]{desOut});
                    input = BitSet.valueOf(new long[]{desOut});
                }
            }
        }

    }

}
