package NeuralNetworkApplication;

import Einlesen.Einlesen;
import Gui.ArrayToImage;
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
        int x = 10;
        neuralNetwork.createHiddenLayer(x,ActivationFunction.ActivationSigmoid);
//        you can add another Hidden Layer if u wish with
        int y = 100;
        neuralNetwork.createHiddenLayer(y,ActivationFunction.ActivationSigmoid);


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
        fillFunction(800,800);
        for(double[] i : function){
            for(double z : i){
                System.out.print(Math.round(z*1000.0)/1000.0 +" ");
            }
            System.out.println();
        }
        neuralNetwork.reset();
        double minWert = 0;
        double maxWert = 0;
        for(double[] i : function){
            for (double z : i){
                if (minWert>z) minWert = z;
                if(maxWert<z) maxWert = z;
            }
        }
        ArrayToImage.createImage(function,1/maxWert,-1/minWert);
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
