package NeuralNetwork.ActivationFunction;

public class Sigmoid implements ActivationFunction{
    @Override
    public double activation(double input) {
        return (1. / (1. +Math.exp(-input)));
    }

    @Override
    public double derivative(double input) {
        double sigmoid = activation(input);
        return sigmoid * (1-sigmoid);
    }
}
