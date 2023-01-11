package NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    List<InputNeuron> inputNeurons =  new ArrayList<>();
    List<WorkingNeuron> outPutNeurons = new ArrayList<>();
    List<WorkingNeuron> hiddenNeurons = new ArrayList<>();
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
    public void createHiddenNeuron(int amount){
        for(int i = 0; i<amount;i++){
            hiddenNeurons.add(new WorkingNeuron());
        }
    }
    public void createBias(){
        biasNeurons.add(new InputNeuron(1));
        biasNeurons.add(new InputNeuron(1));
    }
    public void createFullConnections(){
        addBias();
        if(hiddenNeurons.size() == 0){
            for(WorkingNeuron out : outPutNeurons){
                for(InputNeuron in : inputNeurons){
                    out.addConnection(new Connection(in,1));
                }
            }
        }else{
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenNeurons){
                    out.addConnection(new Connection(hidden,1));
                }
            }
            for(InputNeuron in : inputNeurons){
                for(WorkingNeuron hidden : hiddenNeurons){
                    hidden.addConnection(new Connection(in,1));
                }
            }
        }
    }
    public void createFullConnections(double... weights){
        int i = 0;
        addBias();
        if(hiddenNeurons.size() == 0){
            if (weights.length != inputNeurons.size()*outPutNeurons.size())
                throw new RuntimeException();
            for(WorkingNeuron out : outPutNeurons){
                for(InputNeuron in : inputNeurons){
                    out.addConnection(new Connection(in, weights[i++]));
                }
            }
        }else{
            if (weights.length != hiddenNeurons.size()*outPutNeurons.size() + inputNeurons.size()  * hiddenNeurons.size())
                throw new RuntimeException();
            for(WorkingNeuron hidden : hiddenNeurons){
                for(InputNeuron in : inputNeurons){
                    hidden.addConnection(new Connection(in,weights[i++]));
                }
            }
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenNeurons){
                    out.addConnection(new Connection(hidden,weights[i++]));
                }
            }
        }
    }

    private void addBias() {
        if(biasNeurons.size() == 2){
            for(WorkingNeuron hiddenNeuron: hiddenNeurons) {
                hiddenNeuron.addBiasNeuron(new Connection(biasNeurons.get(0), 1));
            }
            for(WorkingNeuron outPutNeuron: outPutNeurons){
                outPutNeuron.addBiasNeuron(new Connection(biasNeurons.get(1),1));
            }
        }
    }

    public void createFullConnections(boolean random){
        addBias();
        if(hiddenNeurons.size() == 0){
            for(WorkingNeuron out : outPutNeurons){
                for(InputNeuron in : inputNeurons){
                    out.addConnection(new Connection(in, Math.random()*2-1));
                }
            }
        }else{
            for(WorkingNeuron out : outPutNeurons){
                for(WorkingNeuron hidden : hiddenNeurons){
                    out.addConnection(new Connection(hidden,Math.random()*2-1));
                }
            }
            for(InputNeuron in : inputNeurons){
                for(WorkingNeuron hidden : hiddenNeurons){
                    hidden.addConnection(new Connection(in,Math.random()*2-1));
                }
            }
        }
    }
    public void reset(){
        for(WorkingNeuron out : outPutNeurons){
            out.reset();
        }
        for(WorkingNeuron hidden : hiddenNeurons){
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
        if(hiddenNeurons.size() >0){
            for (int i = 0; i < classes.length; i++) {
                outPutNeurons.get(i).backpropagateSmallDelta();
            }
        }
        for (WorkingNeuron outPutNeuron : outPutNeurons) {
            outPutNeuron.deltaLearning(learnRate);
        }

        for (WorkingNeuron hiddenNeuron : hiddenNeurons) {
            hiddenNeuron.deltaLearning(learnRate);
        }


    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (int i = 0;i<inputNeurons.size();i++) {
            text.append(inputNeurons.get(i).toString());
        }
        for (WorkingNeuron hiddenNeuron : hiddenNeurons){
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
