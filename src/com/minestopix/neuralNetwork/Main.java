package com.minestopix.neuralNetwork;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by MinestoPix on 3/3/2015.
 */
public class Main {

    public static List<OutputNeutron> outputNeutronList = new ArrayList<OutputNeutron>();

    public static void main(String[] args) {

        List<Neutron> outputNeutrons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            outputNeutrons.add(new OutputNeutron());
        }


        List<Neutron> hiddenNeutrons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            hiddenNeutrons.add(new HiddenNeutron(outputNeutrons));
        }


        HiddenNeutron inputNeutron1 = new HiddenNeutron(hiddenNeutrons, 0);
        HiddenNeutron inputNeutron2 = new HiddenNeutron(hiddenNeutrons, 0);
        HiddenNeutron inputNeutron3 = new HiddenNeutron(hiddenNeutrons, 0);




        BitSet input;
        BitSet output = new BitSet(3);
        int desOut = 3;
        input = BitSet.valueOf(new long[]{desOut});
        BitSet desired = BitSet.valueOf(new long[]{desOut});


        for (int i = 0; i < 3; i++) {

            output.set(i, outputNeutronList.contains(outputNeutrons.get(i)));

        }


        if (input.get(0))
            inputNeutron1.activate();
        if (input.get(1))
            inputNeutron2.activate();
        if (input.get(2))
            inputNeutron3.activate();

        for (Neutron n : hiddenNeutrons) {
            n.activate();
        }
        for (Neutron n : outputNeutrons) {
            n.activate();
        }

        int iters = 0;

        while (!(output.equals(desired))) {

            for (int i = 0; i < 3; i++) {
                if (!output.get(i) && desired.get(i)) {
                    outputNeutrons.get(i).increaseStrength(null, 0.1f);
                }
                else if (output.get(i) && !desired.get(i)) {
                    outputNeutrons.get(i).increaseStrength(null, -0.1f);
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

            for (Neutron n : hiddenNeutrons) {
                n.activate();
            }
            for (Neutron n : outputNeutrons) {
                n.activate();
            }

            for (int i = 0; i < 3; i++) {

                output.set(i, outputNeutronList.contains(outputNeutrons.get(i)));

            }
            int value = 0;
            for (int i = output.nextSetBit(0); i >= 0; i = output.nextSetBit(i + 1)) {
                value += (1 << i);
            }
//            System.out.println(value);
            iters++;
            if ((output.equals(desired))) {
                if(iters > 10000){
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
