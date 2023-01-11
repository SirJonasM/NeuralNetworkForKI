package NeuralNetworkTest;

import NeuralNetwork.NeuralNetwork;
import NeuralNetwork.InputNeuron;
import NeuralNetwork.WorkingNeuron;
import Einlesen.Einlesen;

import java.io.FileNotFoundException;

public class TestClass {
    public static void main(String[] args) throws FileNotFoundException {
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        InputNeuron in1 = neuralNetwork.createInputNeuron();
        InputNeuron in2 = neuralNetwork.createInputNeuron();

        //neuralNetwork.createHiddenNeuron(100);

        WorkingNeuron out1 = neuralNetwork.createWorkingNeuron();
        WorkingNeuron out2 = neuralNetwork.createWorkingNeuron();
        WorkingNeuron out3 = neuralNetwork.createWorkingNeuron();

        neuralNetwork.createBias();
        neuralNetwork.createFullConnections();
        double[][] inputs = Einlesen.einlesen("src/Einlesen/Iris.txt");

        int epochen = 2_000_000;
        for(int i = 0; i<epochen; i++) {
            int fehler = 0;
            for (double[] input: inputs) {
                in1.setValue(input[0]);
                in2.setValue(input[1]);
                boolean fehlerCalculated = !(out1.getValue() == input[4] && out2.getValue() == input[5] && out3.getValue() == input[6]);
                if(fehlerCalculated)
                    fehler++;

                neuralNetwork.backpropagation(new double[]{input[4],input[5],input[6]}, 0.01);
                neuralNetwork.reset();
            }
            System.out.printf("In der Epoche %d wurde zu %d%% richtig geraten. | (%d/%d)\n",i,(int) (100 - (fehler*100.0)/inputs.length),inputs.length-fehler,inputs.length);
            if(fehler == 0)break;
        }

    }

}
