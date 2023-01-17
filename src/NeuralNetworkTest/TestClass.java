package NeuralNetworkTest;

import Gui.activateScreen;
import NeuralNetwork.*;
import NeuralNetwork.ActivationFunction.*;

import Einlesen.Einlesen;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TestClass {
    private static final double learnrate = 0.0001;
    private static final ActivationFunction activationFunction = ActivationFunction.ActivationBool;
    private static final int features = 4;
    public static final int clazzId = 4;
    private static final int outputs = 3;
    private static final String[] clazzes = new String[]{"1","2","3"};

    public static void main(String[] args) throws FileNotFoundException {
        double[][] inputs = Einlesen.einlesen("src/Einlesen/acceleration.txt",features,clazzes,clazzId);
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        InputNeuron[] inputNeurons = new InputNeuron[features];
        WorkingNeuron[] outputNeurons = new WorkingNeuron[outputs];

        for(int i = 0;i<features;i++){
            inputNeurons[i] = neuralNetwork.createInputNeuron();
        }
        for(int i = 0;i<outputs;i++){
            outputNeurons[i] = neuralNetwork.createOutputNeuron();
        }
        neuralNetwork.createHiddenLayer(10,ActivationFunction.ActivationSigmoid);
        neuralNetwork.createHiddenLayer(11,ActivationFunction.ActivationIdentity);
        neuralNetwork.createHiddenLayer(12,ActivationFunction.ActivationHyperbolicTanget);


        neuralNetwork.createBias();
        neuralNetwork.createFullConnections(true);
        neuralNetwork.setActivationFunctionOutput(activationFunction);

        int fehler;
        int epochen = 5;

        for(int in = 0;in<inputNeurons.length;in++){
            inputNeurons[in].setValue(inputs[0][in]);
        }
        for(int i = 0; i<epochen; i++) {
            fehler = 0;
            for (double[] input: inputs) {
                for(int in = 0;in<inputNeurons.length;in++){
                    inputNeurons[in].setValue(input[in]);
                }
                double[] clazzes = getClazzes(input);
                if(isFalse(outputNeurons,clazzes)) {
                    fehler++;
                }
                neuralNetwork.backpropagation(clazzes, learnrate);
                neuralNetwork.reset();
            }
            if(true) System.out.printf("In der Epoche %d wurde zu %d%% richtig geraten. | (%d/%d)\n",i,(int) (100 - (fehler*100.0)/inputs.length),inputs.length-fehler,inputs.length);
            if(fehler == 0)break;

        }
        inputs = Einlesen.einlesen("src/Einlesen/acceleration2.txt",features,clazzes,clazzId);
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
        double fehlerQuote = ((double)(inputs.length-fehler)/((double) inputs.length));
        fehlerQuote = fehlerQuote*100;
        fehlerQuote = Math.round(fehlerQuote);
        System.out.printf("Evaluation anhand von %d Mustern: %f%% richtig geraten. - (%d/%d) ",inputs.length,fehlerQuote,(inputs.length-fehler),inputs.length);
        List<ArrayList<WorkingNeuron>> hiddenLayers = neuralNetwork.getHiddenLayers();

        //System.out.println(hiddenLayers.get(0).get(0));
        activateScreen.execute(neuralNetwork,inputNeurons, outputNeurons);
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
        return (int) out.getValue();
    }

}
