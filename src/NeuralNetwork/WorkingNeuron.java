package NeuralNetwork;


import NeuralNetwork.ActivationFunction.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class WorkingNeuron extends Neuron {
    private List<Connection> connections = new ArrayList<>();
    private double smallDelta;
    private double value = 0;
    private boolean valueClean = false;
    private Connection biasConnection = null;
    private boolean hasBias = false;

    @Override
    public double getValue(){
        if(valueClean) return value;

        double sum = 0;
        if (hasBias) sum = biasConnection.getValue();
        for (Connection connection : connections) {
            sum += connection.getValue();
        }
        valueClean = true;
        value = activationFunction.activation(sum);
        return value;
    }

    public void reset(){
        value = 0;
        valueClean = false;
        smallDelta = 0;
    }
    public void addConnection(Connection connection){
        connections.add(connection);
    }
    public void calculateDelta(double classes){
        smallDelta = classes - getValue();
    }

    public void backpropagateSmallDelta(){
        for(Connection connection : connections){
            Neuron neuron = connection.getNeuron();
            if(neuron instanceof WorkingNeuron workingNeuron){
                workingNeuron.smallDelta += this.smallDelta * connection.getWeight();
            }
        }
    }

    public void deltaLearning(double learnRate) {
        double bigDeltaFactor = activationFunction.derivative(getValue())* learnRate * smallDelta;
        for(int i = 0; i<connections.size();i++){
            double bigDelta = bigDeltaFactor  *connections.get(i).getNeuron().getValue();
            connections.get(i).updateWeight(bigDelta);
        }
        if(hasBias) biasConnection.updateWeight(bigDeltaFactor);
    }

    public void addBiasNeuron(Connection connection) {
        hasBias = true;
        biasConnection = connection;
    }
    public void setActivationFunction(ActivationFunction activationFunction){
        this.activationFunction = activationFunction;
    }

    @Override
    public String toString() {
        return connections.toString();
    }

    public double getValueRaw() {
        double sum = 0;
        if (hasBias) sum = biasConnection.getValue();
        for (Connection connection : connections) {
            sum += connection.getValue();
        }
        return sum;
    }

}
