package NeuralNetworkApplication;

import Einlesen.Einlesen;
import NeuralNetwork.*;
import NeuralNetwork.ActivationFunction.ActivationFunction;

import java.io.FileNotFoundException;

public class WetterAI {
    static int[][] function = new  int [100][100];
    static int epochen = 20_000;
    static NeuralNetwork neuralNetwork;
    static InputNeuron in1;
    static InputNeuron in2;
    static WorkingNeuron out1;
    private static final double  learnRate = 0.01;

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
//        int y = 100;
//        neuralNetwork.createHiddenLayer(y,ActivationFunction.ActivationSigmoid);


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
        fillFunction();
        for(int[] i : function){
            for(int z : i){
                System.out.print(z);
            }
            System.out.println();
        }
        neuralNetwork.reset();
        in1.setValue(90/100.);
        in2.setValue(90/100.);

        System.out.println(out1.getValue());
    }

    public static void fillFunction(){
        for(int x = 0;x<function.length;x++) {
            for (int y = 0; y < function.length; y++) {
                neuralNetwork.reset();
                in1.setValue(x/100.0);
                in2.setValue(y/100.0);

                function[function.length-y-1][x] = (int) out1.getValue();
            }
        }
    }
}
