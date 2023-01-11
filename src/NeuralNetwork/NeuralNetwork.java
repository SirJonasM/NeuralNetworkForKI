package NeuralNetwork;

import NeuralNetwork.ActivationFunction.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    List<InputNeuron> inputNeurons =  new ArrayList<>();
    List<WorkingNeuron> outPutNeurons = new ArrayList<>();
    List<WorkingNeuron> hiddenNeurons2 = new ArrayList<>();
    List<WorkingNeuron> hiddenNeurons1 = new ArrayList<>();
    List<InputNeuron> biasNeurons = new ArrayList<>();

    public InputNeuron createInputNeuron(){
        InputNeuron in = new InputNeuron();
        inputNeurons.add(in);
        return in;
    }
    public WorkingNeuron createWorkingNeuron(){
        WorkingNeuron out = new WorkingNeuron();
        outPutNeurons.add(out);
        return out;
    }
    public void createHiddenNeurons1(int amount){
        for(int i = 0; i<amount;i++){
            hiddenNeurons1.add(new WorkingNeuron());
        }
    }
    public void createHiddenNeurons2(int amount){
        for(int i = 0; i<amount;i++){
            hiddenNeurons2.add(new WorkingNeuron());
        }
    }
    public void setActivationFunctionHidden1(ActivationFunction activationFunction){
        hiddenNeurons1.forEach(n -> n.setActivationFunction(activationFunction));
    }
    public void setActivationFunctionHidden2(ActivationFunction activationFunction){
        hiddenNeurons2.forEach(n -> n.setActivationFunction(activationFunction));
    }

    public void setActivationFunctionOutput(ActivationFunction activationFunction){
        outPutNeurons.forEach(n -> n.setActivationFunction(activationFunction));
    }
    public void createBias(){
        biasNeurons.add(new InputNeuron(1));
        biasNeurons.add(new InputNeuron(1));
        biasNeurons.add(new InputNeuron(1));
    }
    public void createFullConnections(){
        addBias();
        if(hiddenNeurons1.size() == 0){
            for(WorkingNeuron out : outPutNeurons){
                for(InputNeuron in : inputNeurons){
                    out.addConnection(new Connection(in,1));
                }
            }
        }else if (hiddenNeurons2.size() == 0){
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenNeurons1){
                    out.addConnection(new Connection(hidden,1));
                }
            }
            for(InputNeuron in : inputNeurons){
                for(WorkingNeuron hidden : hiddenNeurons1){
                    hidden.addConnection(new Connection(in,1));
                }
            }
        }else{
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenNeurons2){
                    out.addConnection(new Connection(hidden,1));
                }
            }
            for(WorkingNeuron hidden1 : hiddenNeurons1){
                for(WorkingNeuron hidden2 : hiddenNeurons2){
                    hidden2.addConnection(new Connection(hidden1,1));
                }
            }
            for(InputNeuron in : inputNeurons){
                for(WorkingNeuron hidden : hiddenNeurons1){
                    hidden.addConnection(new Connection(in,1));
                }
            }
        }
    }
    public void createFullConnections(double... weights){
        int i = 0;
        addBias();
        if(hiddenNeurons1.size() == 0){
            if (weights.length != inputNeurons.size()*outPutNeurons.size())
                throw new RuntimeException();
            for(WorkingNeuron out : outPutNeurons){
                for(InputNeuron in : inputNeurons){
                    out.addConnection(new Connection(in, weights[i++]));
                }
            }
        }else{
            if (weights.length != hiddenNeurons1.size()*outPutNeurons.size() + inputNeurons.size()  * hiddenNeurons1.size())
                throw new RuntimeException();
            for(WorkingNeuron hidden : hiddenNeurons1){
                for(InputNeuron in : inputNeurons){
                    hidden.addConnection(new Connection(in,weights[i++]));
                }
            }
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenNeurons1){
                    out.addConnection(new Connection(hidden,weights[i++]));
                }
            }
        }
    }

    private void addBias() {
        if(biasNeurons.size() == 3){
            for(WorkingNeuron hiddenNeuron: hiddenNeurons1) {
                hiddenNeuron.addBiasNeuron(new Connection(biasNeurons.get(0), 1));
            }
            for(WorkingNeuron outPutNeuron: outPutNeurons){
                outPutNeuron.addBiasNeuron(new Connection(biasNeurons.get(1),1));
            }
            for(WorkingNeuron hiddenNeuron: hiddenNeurons2) {
                hiddenNeuron.addBiasNeuron(new Connection(biasNeurons.get(2), 1));
            }
        }
    }

    public void createFullConnections(boolean random){
        addBias();
        if(hiddenNeurons1.size() == 0){
            for(WorkingNeuron out : outPutNeurons){
                for(InputNeuron in : inputNeurons){
                    out.addConnection(new Connection(in, Math.random()*2-1));
                }
            }
        }else{
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenNeurons1){
                    out.addConnection(new Connection(hidden,Math.random()*2-1));
                }
            }
            for(InputNeuron in : inputNeurons){
                for(WorkingNeuron hidden : hiddenNeurons1){
                    hidden.addConnection(new Connection(in,Math.random()*2-1));
                }
            }
        }
    }
    public void reset(){
        for(WorkingNeuron out : outPutNeurons){
            out.reset();
        }
        for(WorkingNeuron hidden : hiddenNeurons1){
            hidden.reset();
        }
        for(WorkingNeuron hidden : hiddenNeurons2){
            hidden.reset();
        }
    }

    public void backpropagation(double[] classes, double learnRate){
        if(classes.length != outPutNeurons.size())
            throw new IllegalArgumentException();
        reset();

        for(int i = 0; i<classes.length;i++){
            outPutNeurons.get(i).calculateDelta(classes[i]);
        }
        if(hiddenNeurons2.size() >0){
            for (int i = 0; i < outPutNeurons.size(); i++) {
                outPutNeurons.get(i).backpropagateSmallDelta();
            }
            for (int i = 0; i < hiddenNeurons1.size(); i++) {
                hiddenNeurons2.get(i).backpropagateSmallDelta();
            }
        }else if(hiddenNeurons1.size() >0){
            for (int i = 0; i < classes.length; i++) {
                outPutNeurons.get(i).backpropagateSmallDelta();
            }
        }
        for (WorkingNeuron outPutNeuron : outPutNeurons) {
            outPutNeuron.deltaLearning(learnRate);
        }
        for (WorkingNeuron hiddenNeuron : hiddenNeurons2) {
            hiddenNeuron.deltaLearning(learnRate);
        }
        for (WorkingNeuron hiddenNeuron : hiddenNeurons1) {
            hiddenNeuron.deltaLearning(learnRate);
        }


    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (int i = 0;i<inputNeurons.size();i++) {
            text.append(inputNeurons.get(i).toString());
        }
        for (WorkingNeuron hiddenNeuron : hiddenNeurons1){
            text.append(hiddenNeuron);
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
