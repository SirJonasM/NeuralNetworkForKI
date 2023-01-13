package NeuralNetworkTest;

import NeuralNetwork.*;
import NeuralNetwork.ActivationFunction.*;

import Einlesen.Einlesen;

import java.io.FileNotFoundException;

public class TestClass {
    private static final double learnrate = 0.0001;
    private static final ActivationFunction activationFunction = ActivationFunction.ActivationBool;
    private static final int features = 4;
    public static final int clazzId = 4;
    private static final int outputs = 3;

    public static void main(String[] args) throws FileNotFoundException {
        double[][] inputs = Einlesen.einlesen("src/Einlesen/Iris.txt",features,new String[]{"1","2","3"},clazzId);
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        InputNeuron[] inputNeurons = new InputNeuron[features];
        WorkingNeuron[] outputNeurons = new WorkingNeuron[outputs];

        for(int i = 0;i<features;i++){
            inputNeurons[i] = neuralNetwork.createInputNeuron();
        }
        for(int i = 0;i<outputs;i++){
            outputNeurons[i] = neuralNetwork.createWorkingNeuron();
        }
        neuralNetwork.createHiddenNeurons1(100);
        neuralNetwork.setActivationFunctionHidden1(ActivationFunction.ActivationIdentity);
        neuralNetwork.createHiddenNeurons2(10);
        neuralNetwork.setActivationFunctionHidden2(ActivationFunction.ActivationHyperbolicTanget);

        neuralNetwork.setActivationFunctionOutput(ActivationFunction.ActivationSigmoid);

        neuralNetwork.createBias();
        neuralNetwork.createFullConnections(true);
        int fehler;
        int epochen = 20000;
        for(int i = 0; i<epochen; i++) {
            fehler = 0;
            for (double[] input: inputs) {
                for(int in = 0;in<inputNeurons.length;in++){
                    inputNeurons[in].setValue(input[in]);
                }
                double[] clazzes = getClazzes(input);
                if(isFalse(outputNeurons,clazzes)) {
                    fehler++;
                    neuralNetwork.backpropagation(clazzes, learnrate);
                }
                neuralNetwork.reset();
            }
            if(i%100 == 0) System.out.printf("In der Epoche %d wurde zu %d%% richtig geraten. | (%d/%d)\n",i,(int) (100 - (fehler*100.0)/inputs.length),inputs.length-fehler,inputs.length);
            if(fehler == 0)break;
        }
        inputs = Einlesen.einlesen("src/Einlesen/Iris.txt",features,new String[]{"1","2","3"},clazzId);
        fehler = 0;
        for (double[] input: inputs) {
            for(int in = 0;in<inputNeurons.length;in++){
                inputNeurons[in].setValue(input[in]);
            }
            double[] clazzes = getClazzes(input);
            if(isFalse(outputNeurons,clazzes))
                fehler++;
            neuralNetwork.reset();
        }
        System.out.printf("Evaluation anhand von %d Mustern: ",inputs.length);
        System.out.println(((double)(inputs.length-fehler)/(double) inputs.length)*100  + "%");
    }

    private static double[] getClazzes(double[] input) {
        double[] r = new double[input.length-clazzId];
        System.arraycopy(input,clazzId,r,0,r.length);
        return r;
    }


    private static boolean isFalse(WorkingNeuron[] outputNeurons, double[] clazzes) {
        for(int i = 0;i<outputNeurons.length;i++){
            int out = getOutput(outputNeurons[i]);
            if(out != clazzes[i]) return true;
        }
        return false;

    }

    public static int getOutput(WorkingNeuron out){
        return (int) activationFunction.activation(out.getValue()-0.5);
    }

}
