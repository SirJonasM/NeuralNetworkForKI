package NeuralNetwork;

public class InputNeuron extends Neuron {
    double value;
    @Override
    public double getValue(){
        return value;
    }
    public InputNeuron(){

    }
    public InputNeuron(int value){
        this.value = value;
    }
    public void setValue(double value){
        this.value = value;
    }

    @Override
    public String toString() {
        return "InputNeuron: "+ value;
    }
}
