package Gui;

import javax.swing.*;

public class activateScreen {

    public static void main(String[] args) {
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
