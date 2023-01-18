package Gui;

import NeuralNetwork.InputNeuron;
import NeuralNetwork.NeuralNetwork;
import NeuralNetwork.WorkingNeuron;

import javax.swing.*;

public class activateScreen extends Thread {

    public void run(){
        execute();
    }
    public static void execute() {

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
}
