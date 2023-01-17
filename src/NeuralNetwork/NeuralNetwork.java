package NeuralNetwork;

import NeuralNetwork.ActivationFunction.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    List<InputNeuron> inputNeurons =  new ArrayList<>();
    List<ArrayList<WorkingNeuron>> hiddenLayers = new ArrayList<>();
    List<WorkingNeuron> outPutNeurons = new ArrayList<>();
    List<InputNeuron> biasNeurons = new ArrayList<>();

    public InputNeuron createInputNeuron(){
        InputNeuron in = new InputNeuron();
        inputNeurons.add(in);
        return in;
    }
    public WorkingNeuron createOutputNeuron(){
        WorkingNeuron out = new WorkingNeuron();
        outPutNeurons.add(out);
        return out;
    }
    public void createHiddenLayer(int amount,ActivationFunction activationFunction){
        ArrayList<WorkingNeuron> hiddens = new ArrayList<>();
        for(int i = 0; i<amount;i++){
            WorkingNeuron workingNeuron = new WorkingNeuron();
            workingNeuron.setActivationFunction(activationFunction);
            hiddens.add(workingNeuron);
        }
        hiddenLayers.add(hiddens);

    }
    public void setActivationFunctionOutput(ActivationFunction activationFunction){
        outPutNeurons.forEach(n -> n.setActivationFunction(activationFunction));
    }
    public void createBias(){
        for(int i = 0; i<hiddenLayers.size()+1;i++) {
            biasNeurons.add(new InputNeuron(1));
        }
    }
    public void createFullConnections(){
        addBias();
        if(hiddenLayers.size() == 0){
            for(WorkingNeuron out : outPutNeurons){
                for(InputNeuron in : inputNeurons){
                    out.addConnection(new Connection(in,1));
                }
            }
        }else if (hiddenLayers.size() == 1){
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenLayers.get(0)){
                    out.addConnection(new Connection(hidden,1));
                }
            }
            for(InputNeuron in : inputNeurons){
                for(WorkingNeuron hidden : hiddenLayers.get(0)){
                    hidden.addConnection(new Connection(in,1));
                }
            }
        }else{
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenLayers.get(hiddenLayers.size()-1)){
                    out.addConnection(new Connection(hidden,1));
                }
            }
            for(int i = 0;i<hiddenLayers.size()-1;i++) {
                for (WorkingNeuron hidden1 : hiddenLayers.get(i)) {
                    for (WorkingNeuron hidden2 : hiddenLayers.get(i+1)) {
                        hidden2.addConnection(new Connection(hidden1, 1));
                    }
                }
            }
            for(InputNeuron in : inputNeurons){
                for(WorkingNeuron hidden : hiddenLayers.get(0)){
                    hidden.addConnection(new Connection(in,1));
                }
            }
        }
    }
    public void createFullConnections(double... weights){
        addBias();
        if(hiddenLayers.size() == 0){
            for(WorkingNeuron out : outPutNeurons){
                for(InputNeuron in : inputNeurons){
                    out.addConnection(new Connection(in,1));
                }
            }
        }else if (hiddenLayers.size() == 1){
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenLayers.get(0)){
                    out.addConnection(new Connection(hidden,1));
                }
            }
            for(InputNeuron in : inputNeurons){
                for(WorkingNeuron hidden : hiddenLayers.get(0)){
                    hidden.addConnection(new Connection(in,1));
                }
            }
        }else{
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenLayers.get(hiddenLayers.size()-1)){
                    out.addConnection(new Connection(hidden,1));
                }
            }
            for(int i = 0;i<hiddenLayers.size()-1;i++) {
                for (WorkingNeuron hidden1 : hiddenLayers.get(i)) {
                    for (WorkingNeuron hidden2 : hiddenLayers.get(i+1)) {
                        hidden2.addConnection(new Connection(hidden1, 1));
                    }
                }
            }
            for(InputNeuron in : inputNeurons){
                for(WorkingNeuron hidden : hiddenLayers.get(0)){
                    hidden.addConnection(new Connection(in,1));
                }
            }
        }
    }

    private void addBias() {
        if(biasNeurons.size() == hiddenLayers.size()+1){
            for(WorkingNeuron outPutNeuron: outPutNeurons){
                outPutNeuron.addBiasNeuron(new Connection(biasNeurons.get(0),1));
            }
            int bias = 1;
            for(ArrayList<WorkingNeuron> hiddens : hiddenLayers) {
                for (WorkingNeuron hiddenNeuron : hiddens) {
                    hiddenNeuron.addBiasNeuron(new Connection(biasNeurons.get(bias), 1));
                }
                bias++;
            }
        }
    }

    public void createFullConnections(boolean random){
        addBias();
        if(hiddenLayers.size() == 0){
            for(WorkingNeuron out : outPutNeurons){
                for(InputNeuron in : inputNeurons){
                    out.addConnection(new Connection(in,Math.random()*2-1));
                }
            }
        }else if (hiddenLayers.size() == 1){
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenLayers.get(0)){
                    out.addConnection(new Connection(hidden,Math.random()*2-1));
                }
            }
            for(InputNeuron in : inputNeurons){
                for(WorkingNeuron hidden : hiddenLayers.get(0)){
                    hidden.addConnection(new Connection(in,Math.random()*2-1));
                }
            }
        }else{
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenLayers.get(hiddenLayers.size()-1)){
                    out.addConnection(new Connection(hidden,Math.random()*2-1));
                }
            }
            for(int i = 0;i<hiddenLayers.size()-1;i++) {
                for (WorkingNeuron hidden1 : hiddenLayers.get(i)) {
                    for (WorkingNeuron hidden2 : hiddenLayers.get(i+1)) {
                        hidden2.addConnection(new Connection(hidden1, Math.random()*2-1));
                    }
                }
            }
            for(InputNeuron in : inputNeurons){
                for(WorkingNeuron hidden : hiddenLayers.get(0)){
                    hidden.addConnection(new Connection(in,Math.random()*2-1));
                }
            }
        }

    }
    public void reset(){
        for(WorkingNeuron out : outPutNeurons){
            out.reset();
        }
        for(ArrayList<WorkingNeuron> hiddens : hiddenLayers) {
            for (WorkingNeuron hidden : hiddens) {
                hidden.reset();
            }
        }

    }

    public void backpropagation(double[] classes, double learnRate){
        if(classes.length != outPutNeurons.size())
            throw new IllegalArgumentException();
        reset();

        for(int i = 0; i<classes.length;i++){
            outPutNeurons.get(i).calculateDelta(classes[i]);
        }
        if(hiddenLayers.size() > 0){
            for (WorkingNeuron outPutNeuron : outPutNeurons) {
                outPutNeuron.backpropagateSmallDelta();
            }
            for(int i = hiddenLayers.size()-1; i>=0;i--){
                for(WorkingNeuron workingNeuron : hiddenLayers.get(i)){
                    workingNeuron.backpropagateSmallDelta();
                }
            }
        }
        for (WorkingNeuron outPutNeuron : outPutNeurons) {
            outPutNeuron.deltaLearning(learnRate);
        }
        for(ArrayList<WorkingNeuron> hiddens : hiddenLayers) {
            for (WorkingNeuron hiddenNeuron : hiddens) {
                hiddenNeuron.deltaLearning(learnRate);
            }
        }


    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (InputNeuron inputNeuron : inputNeurons) {
            text.append(inputNeuron.toString());
        }
        for (WorkingNeuron outputNeuron : outPutNeurons){
            text.append(outputNeuron);
        }
        for (InputNeuron biasNeuron : biasNeurons){
            text.append(biasNeuron);
        }
        return text.toString();
    }
}
