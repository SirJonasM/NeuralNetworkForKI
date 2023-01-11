package NeuralNetwork.ActivationFunction;

public interface ActivationFunction {
    Identity ActivationIdentity = new Identity();
    Bool ActivationBool =  new Bool();
    Sigmoid ActivationSigmoid = new Sigmoid();

    public double activation(double input);
    public double derivative(double input);
}
