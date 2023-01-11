package NeuralNetwork;

import NeuralNetwork.ActivationFunction.ActivationFunction;

public abstract class Neuron {
    public abstract double getValue();
    ActivationFunction activationFunction = ActivationFunction.ActivationBool;



}
