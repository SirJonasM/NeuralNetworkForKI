package NeuralNetwork.ActivationFunction;

public class Bool implements ActivationFunction {
    @Override
    public double activation(double input) {
        return input>0.0 ? 1 : 0;
    }

    @Override
    public double derivative(double input) {
        return 1;
    }
}
