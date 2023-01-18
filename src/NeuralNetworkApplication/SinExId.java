package NeuralNetworkApplication;

import Einlesen.Einlesen;
import Gui.ArrayToImage;
import NeuralNetwork.*;
import NeuralNetwork.ActivationFunction.ActivationFunction;
import NeuralNetworkTest.TestClass;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class SinExId {
    static NeuralNetwork neuralNetwork;
    static InputNeuron in1;
    static InputNeuron in2;
    static WorkingNeuron out1;
    static WorkingNeuron out2;
    static WorkingNeuron out3;
    private static double epochen = 100;
    private static double learnRate = 0.00001;
    private static double[][][] function;


    public static void main(String[] args) throws FileNotFoundException {
        double[][] inputs = Einlesen.einlesen("src/Einlesen/sinExId1.txt",2,new String[]{"sinus","quadratic","identity"},2);

        neuralNetwork = new NeuralNetwork();
        in1 = neuralNetwork.createInputNeuron();
        in2 = neuralNetwork.createInputNeuron();

        neuralNetwork.createHiddenLayer(200,ActivationFunction.ActivationSigmoid);
        neuralNetwork.createHiddenLayer(200,ActivationFunction.ActivationHyperbolicTanget);


        out1 = neuralNetwork.createOutputNeuron();
        out2 = neuralNetwork.createOutputNeuron();
        out3 = neuralNetwork.createOutputNeuron();

        neuralNetwork.setActivationFunctionOutput(ActivationFunction.ActivationBool);

        neuralNetwork.createBias();

        neuralNetwork.createFullConnections(true);

        // Train:
        // Einlesen reads the Data into a two-dimensional Array where the rows are the pattern
        // the first two columns are the features and the last one is the class
        for(int epoche = 0; epoche < epochen;epoche++) {
            int fails = 0;
            int fails1 = 0;
            int fails2 = 0;
            int fails3 = 0;
            for (double[] input : inputs) {
                //Setting the input
                in1.setValue(input[0]);
                in2.setValue(input[1]);
                double o1 = out1.getValue();
                double o2 = out2.getValue();
                double o3 = out3.getValue();

                if(o1 != input[2] || o2 != input[3] || o3 != input[4])
                    fails++;
                if(o1 != input[2] ){
                    fails1++;
                }
                if(o2 != input[3]){
                    fails2++;
                }
                if(o3 != input[4]){
                    fails3++;
                }
                neuralNetwork.backpropagation(new double[]{input[2],input[3],input[4]},learnRate);
                neuralNetwork.reset();
            }
            if(fails1 + fails2 +fails3 == 0) break;
            System.out.println(epoche + ": "+ fails +"  sinus: " +fails1 + " quadratic: " + fails2 + " identity: " +fails3 );
        }
        inputs = Einlesen.einlesen("src/Einlesen/sinExId2.txt",2,new String[]{"sinus","quadratic","identity"},2);

        //Test:
        int fails = 0;
        for (double[] input : inputs) {
            neuralNetwork.reset();

            //Setting the input
            in1.setValue(input[0]);
            in2.setValue(input[1]);

            double o1 = out1.getValue();
            double o2 = out2.getValue();
            double o3 = out3.getValue();

            if(o1 != input[2] || o2 != input[3] || o3 != input[4]){
                fails++;
            }
        }
        System.out.println("tested: " + fails);
        fillFunction(500,1000);
        neuralNetwork.reset();
        double[] minWert = new double[]{0,0,0};
        double[] maxWert = new double[]{0,0,0};
        for(double[][] i : function){
            for (double[] z : i){
                for(int q = 0;q<z.length;q++) {
                    if (minWert[q] > z[q])minWert[q] = z[q];
                    if (maxWert[q] < z[q]) maxWert[q] = z[q];
                }
            }
        }
        ArrayToImage.createImage(function,maxWert,minWert);
    }
    public static void fillFunction(double x, double y){
        int midY = (int)y/2;
        int midX = (int)x/2;

        function = new double[(int) y][(int) x][3];
        for(int i = -midX;i<midX-1;i++) {
            for (int z = -midY; z < midY-1; z++) {
                neuralNetwork.reset();
                in1.setValue(i/10.0/Einlesen.maximas[1]);
                in2.setValue(z/10.0/Einlesen.maximas[1]);

                function[function.length-(z+midY)-1][i+midX][0] =  out1.getValueRaw();
                function[function.length-(z+midY)-1][i+midX][1] =  out2.getValueRaw();
                function[function.length-(z+midY)-1][i+midX][2] =  out3.getValueRaw();
            }
        }
    }
}
