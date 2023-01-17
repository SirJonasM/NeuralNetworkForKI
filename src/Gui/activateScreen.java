package Gui;

import NeuralNetwork.InputNeuron;
import NeuralNetwork.WorkingNeuron;

import javax.swing.*;

public class activateScreen {
    static InputNeuron[] inputNeuronsLocal;
    static WorkingNeuron[] outputNeuronsLocal;
    public static void execute(InputNeuron[] inputNeurons, WorkingNeuron[] outputNeurons) {
        inputNeuronsLocal = inputNeurons;
        outputNeuronsLocal = outputNeurons;
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Neuronales Netz");
        window.setSize(800, 500);

        Screen screen = new Screen();
        window.add(screen);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        screen.startMyThread();
    }

    public static InputNeuron[] getInputNeuronsLocal() {
        return inputNeuronsLocal;
    }
    public static WorkingNeuron[] getOutputNeuronsLocal() {
        return outputNeuronsLocal;
    }
}
