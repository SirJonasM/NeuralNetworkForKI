package NeuralNetwork;

public class Connection {
    private Neuron neuron;
    private double weight;
    private double momentum = 0;

    public Connection(Neuron neuron, double weight){
        this.neuron = neuron;
        this.weight = weight;
    }
    public double getValue(){
        return neuron.getValue() * weight;
    }

    public void updateWeight(double update) {
        momentum += update;
        momentum *= 0.9;
        this.weight += update+momentum;
    }

    public Neuron getNeuron() {
        return neuron;
    }

    public double getWeight() {
        return weight;
    }

    public double getIndex(){
        return weight;
    }
    @Override
    public String toString(){
        return Double.toString(weight);
    }
}
