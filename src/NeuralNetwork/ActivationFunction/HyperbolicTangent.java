package NeuralNetwork.ActivationFunction;

public class HyperbolicTangent implements ActivationFunction{
    @Override
    public double activation(double input) {
        double epx = Math.pow(Math.E,input);
        double enx = Math.pow(Math.E,-input);

        return (epx - enx)/(epx+enx);

    }

    @Override
    public double derivative(double input) {
        double tan = activation(input);
        return 1 - tan * tan;
    }
}
