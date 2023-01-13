package NeuralNetworkTest;

import NeuralNetwork.ActivationFunction.ActivationFunction;
import NeuralNetwork.ActivationFunction.Sigmoid;
import NeuralNetwork.NeuralNetwork;
import NeuralNetwork.InputNeuron;
import NeuralNetwork.WorkingNeuron;
import Einlesen.Einlesen;

import java.io.FileNotFoundException;

public class TestClass {
    private static double learnrate = 0.0001;
    private static ActivationFunction activationFunction = ActivationFunction.ActivationBool;

    public static void main(String[] args) throws FileNotFoundException {
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        InputNeuron in1 = neuralNetwork.createInputNeuron();
        InputNeuron in2 = neuralNetwork.createInputNeuron();
//        InputNeuron in3 = neuralNetwork.createInputNeuron();
//        InputNeuron in4 = neuralNetwork.createInputNeuron();

        neuralNetwork.createHiddenNeurons1(100);
        neuralNetwork.setActivationFunctionHidden1(ActivationFunction.ActivationIdentity);
        neuralNetwork.createHiddenNeurons2(10);
        neuralNetwork.setActivationFunctionHidden2(ActivationFunction.ActivationHyperbolicTanget);

        WorkingNeuron out1 = neuralNetwork.createWorkingNeuron();
//        WorkingNeuron out2 = neuralNetwork.createWorkingNeuron();
//        WorkingNeuron out3 = neuralNetwork.createWorkingNeuron();
        neuralNetwork.setActivationFunctionOutput(ActivationFunction.ActivationSigmoid);

        neuralNetwork.createBias();
        neuralNetwork.createFullConnections(true);
        double[][] inputs = Einlesen.einlesen("src/Einlesen/wetter.txt",new int[]{0,1},new String[]{"0","1"},2);
        int fehler;
        int epochen = 20000;
        for(int i = 0; i<epochen; i++) {
            fehler = 0;
            for (double[] input: inputs) {
                in1.setValue(input[0]);
                in2.setValue(input[1]);
//                in3.setValue(input[2]);
//                in4.setValue(input[3]);

//                boolean fehlerCalculated = !(out1.getValue() == input[4] && out2.getValue() == input[5] && out3.getValue() == input[6]);
                int out = (int) activationFunction.activation(out1.getValue()-0.5);
                boolean fehlerCalculated = out == input[2];
                if(!fehlerCalculated) {
                    fehler++;

                    neuralNetwork.backpropagation(new double[]{input[2]}, learnrate);
                }
                neuralNetwork.reset();
            }
            if(i%100 == 0)
            System.out.printf("In der Epoche %d wurde zu %d%% richtig geraten. | (%d/%d)\n",i,(int) (100 - (fehler*100.0)/inputs.length),inputs.length-fehler,inputs.length);
            if(fehler == 0)break;
        }
        inputs = Einlesen.einlesen("src/Einlesen/wetter2.txt",new int[]{0,1},new String[]{"0","1"},2);
        fehler = 0;
        for (double[] input: inputs) {
            in1.setValue(input[0]);
            in2.setValue(input[1]);
//            in3.setValue(input[2]);
//            in4.setValue(input[3]);

//            boolean fehlerCalculated = !(out1.getValue() == input[4] && out2.getValue() == input[5] && out3.getValue() == input[6]);
            int out = (int) activationFunction.activation(out1.getValue()-0.5);
            boolean fehlerCalculated = out != input[2];

            if(fehlerCalculated)
                fehler++;
            neuralNetwork.reset();
        }
        System.out.printf("Evaluation anhand von %d Mustern: ",inputs.length);
        System.out.println(((double)(inputs.length-fehler)/(double) inputs.length)*100  + "%");
    }

}
