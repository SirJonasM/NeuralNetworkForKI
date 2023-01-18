package NeuralNetworkApplication;

import Einlesen.Einlesen;
import Gui.ArrayToImage;
import Gui.activateScreen;
import NeuralNetwork.*;
import NeuralNetwork.ActivationFunction.ActivationFunction;

import java.io.FileNotFoundException;

public class WetterAI {
    static double[][] function;
    static int epochen = 20_000;
    static NeuralNetwork neuralNetwork;
    static InputNeuron in1;
    static InputNeuron in2;
    static WorkingNeuron out1;
    private static final double  learnRate = 0.0001;

    public static void main(String[] args) throws FileNotFoundException {
        //Creating Neural Network
        neuralNetwork = new NeuralNetwork();

//        Adding 2 Input Neurons to the Network
        in1 = neuralNetwork.createInputNeuron();
        in2 = neuralNetwork.createInputNeuron();

//        Adding x Hidden Neurons in one Hidden Layer
        int x = 2;
        neuralNetwork.createHiddenLayer(x,ActivationFunction.ActivationSigmoid);
//        you can add another Hidden Layer if u wish with
        int y = 2;
        neuralNetwork.createHiddenLayer(y,ActivationFunction.ActivationHyperbolicTanget);


//        Adding a Output Neuron
        out1 = neuralNetwork.createOutputNeuron();
        out1.setActivationFunction(ActivationFunction.ActivationBool);

        // Build the Connections
        neuralNetwork.createFullConnections(true);

        // Train:
        // Einlesen reads the Data into a two-dimensional Array where the rows are the pattern
        // the first two columns are the features and the last one is the class
        double[][] inputs = Einlesen.einlesenWetter("src/Einlesen/wetter.txt");
        for(int epoche = 0; epoche < epochen;epoche++) {
            int fails = 0;
            for (double[] input : inputs) {
                //Setting the input
                in1.setValue(input[0]);
                in2.setValue(input[1]);
                double o1 = out1.getValue();

                if(o1 != input[2]){
                    fails++;
                }

                neuralNetwork.backpropagation(new double[]{input[2]},learnRate);
                neuralNetwork.reset();
            }
            if(fails == 0) break;
            System.out.println(fails);
        }
        inputs = Einlesen.einlesenWetter("src/Einlesen/wetter.txt");

        //Test:
        int fails = 0;
        for (double[] input : inputs) {
            neuralNetwork.reset();

            //Setting the input
            in1.setValue(input[0]);
            in2.setValue(input[1]);

            double o1 = out1.getValue();

            if(o1 != input[2]){
                fails++;
            }
        }
        System.out.println("tested: " + fails);
        fillFunction(100,100);
//        for(double[] i : function){
//            for(double z : i){
//                System.out.print(Math.round(z*1000.0)/1000.0 +" ");
//            }
//            System.out.println();
//        }
        neuralNetwork.reset();
        double minValue = 0;
        double maxValue = 0;
        for(double[] i : function){
            for (double z : i){
                if (minValue>z) minValue = z;
                if(maxValue<z) maxValue = z;
            }
        }
        ArrayToImage.createImage(function,1/maxValue,1/minValue);
        activateScreen s = new activateScreen(neuralNetwork);
        s.start();
    }

    public static void fillFunction(double x, double y){
        function = new double[(int) y][(int) x];
        for(int i = 0;i<x;i++) {
            for (int z = 0; z < x; z++) {
                neuralNetwork.reset();
                in1.setValue(i/x);
                in2.setValue(z/y);
                function[function.length-z-1][i] =  out1.getValueRaw();
            }
        }
    }
}
