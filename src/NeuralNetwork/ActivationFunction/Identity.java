package NeuralNetwork.ActivationFunction;

public class Identity implements ActivationFunction{
    @Override
    public double activation(double input) {
        return input;
    }

    @Override
    public double derivative(double input) {
        return 1;
    }
}
