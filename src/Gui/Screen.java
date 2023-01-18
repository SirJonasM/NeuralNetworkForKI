package Gui;

import NeuralNetwork.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import NeuralNetwork.*;
import NeuralNetworkTest.TestClass;

public class Screen extends JPanel implements Runnable, KeyListener, MouseListener {


    final int screenWidth = 1000;
    final int screenHeight = 700;

    final int neuronSize = 10;
    //InputNeuron[] inputNeurons = activateScreen.getInputNeuronsLocal();
    //WorkingNeuron[] outputNeurons = activateScreen.getOutputNeuronsLocal();
    //List<ArrayList<WorkingNeuron>> hiddenLayers = new ArrayList<>();

    List<Integer> neuronPos = new ArrayList<>();
    int length;
    int Column = -1;
    static int[] color = {0, 0, 255};

    public static double f = 0;
    public static int time = 0;

    double FPS = 5;

    Thread myThread;

    static NeuralNetwork neuralNetwork;

    private JPanel MainPanel;

    //public Screen(NeuralNetwork neuralNetwork) {
    public Screen() {
        neuralNetwork = activateScreen.neuralNetwork;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        setLayout(new BorderLayout(0, 0));
    }

    public void startMyThread() {
        myThread = new Thread(this);
        myThread.start();
    }

    @Override
    public void run() {

        while (myThread != null) {
            update();

            double drawInterval = 1_000_000_000 / FPS; // 0.01666 seconds
            double nextDrawTime = System.nanoTime() + drawInterval;

            update();
            repaint();

            try {
                double remainingTime = nextDrawTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                time++;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        //Random rand = new Random();
        //Graphics g2 = (Graphics2D) g;
        //g.drawOval(10, 10, 10, 10);
        //drawNeurons(10, 200, g);
        //drawNeurons(7, 300, g);
        //g.drawString((AttributedCharacterIterator) NeuralNetwork.getInputNeurons(), 10, 10);
        //inputNeurons = activateScreen.getInputNeuronsLocal();
        //outputNeurons = activateScreen.getOutputNeuronsLocal();
        //hiddenLayers = neuralNetwork.getHiddenLayers();
        //System.out.println(hiddenLayers.get(0).get(0));
        //System.out.println(hiddenLayers.get(0).get(0).getConnection(0));

        //System.out.print("\n- - - PaintComponent - - - ");
        int n = neuralNetwork.hiddenLayers.size();
        //int n = hiddenLayers.size();
        int[] neuronsPerColumn = new int[n + 2];
        neuronsPerColumn[0] = neuralNetwork.getInputNeurons().size();
        //neuronsPerColumn[0] = inputNeurons.length;
        for (int i = 1; i < n + 1; i++) {
            //neuronsPerColumn[i] = hiddenLayers.get(i-1).size();
            neuronsPerColumn[i] = neuralNetwork.hiddenLayers.get(i - 1).size();
        }
        //neuronsPerColumn[n+1] = outputNeurons.length;
        neuronsPerColumn[n + 1] = neuralNetwork.getOutputNeurons().size();
        drawAllNeurons(neuronsPerColumn, g);
    }

    public void drawAllNeurons(int[] neuronsPerColumn, Graphics g) {
        int status = 0;
        /*
        status:
        <0|>2 = nothing
        1 = drawHiddenConnections
        2 = drawOutputConnections
         */
        int xPos = screenWidth / (neuronsPerColumn.length + 1);
        for (int i = 0; i < neuronsPerColumn.length; i++) {
            if (i == 1) {
                status = 1;
            }
            if (i == neuronsPerColumn.length - 1) {
                status = 2;
            }
            drawNeurons(neuronsPerColumn[i], xPos + (i * xPos), g, status, xPos);
            /*
            if(i>0 && i<neuronsPerColumn.length-1){
                drawHiddenConnections(g);
            } else if (i==neuronsPerColumn.length-1) {
                drawOutputConnections(g);
            }
             */
            Column++;

        }
        Column = -1;
    }

    public void drawNeurons(int n, int xPos, Graphics g, int status, int xPosFactor) {
        //n -> Anzahl Neuronen in 1 HiddenLayer
        if (n == 0) {
            return;
        }

        int yPos = (screenHeight) / (n + 1);
        for (int i = 0; i < n; i++) {
            g.setColor(Color.WHITE);
            g.fillOval(xPos, yPos + (i * yPos), neuronSize, neuronSize);
            if (status == 2) {
                drawOutputConnections(g, xPos, yPos + (i * yPos), xPos - xPosFactor, i);
            } else if (status == 1) {
                drawHiddenConnections(g, xPos, yPos + (i * yPos), xPos - xPosFactor, i);
            }
        }
        neuronPos.clear();
        for (int i = 0; i < n; i++) {
            length = n;
            neuronPos.add(yPos + (i * yPos));
        }

    }

    public void drawHiddenConnections(Graphics g, int xPos, int yPos, int xPosPrev, int neuronID) {
        for (int i = 0; i < length; i++) {
            double weight = neuralNetwork.hiddenLayers.get(Column).get(neuronID).getConnection(i);
            //System.out.println("Column: " + Column + " neuronID: " + neuronID + " i: " + i);
            //System.out.println(weight);
            //System.out.println("HiddenLayerNeuron Weight: "+weight);
            setColor(weight);

            g.setColor(new Color(color[0], color[1], color[2]));
            g.drawLine(xPos + neuronSize / 2, yPos + neuronSize / 2, xPosPrev + neuronSize / 2, neuronPos.get(i) + neuronSize / 2);
        }
    }

    public void drawOutputConnections(Graphics g, int xPos, int yPos, int xPosPrev, int neuronID) {
        for (int i = 0; i < length; i++) {
            double weight = neuralNetwork.getOutputNeurons().get(neuronID).getConnection(i);
            //System.out.println("OutputNeuron Weight: "+weight);
            setColor(weight);
            g.setColor(new Color(color[0], color[1], color[2]));
            g.drawLine(xPos + neuronSize / 2, yPos + neuronSize / 2, xPosPrev + neuronSize / 2, neuronPos.get(i) + neuronSize / 2);
        }
    }


    public static void setColor(double value) {
        double c = value;
        c *= 0.5;
        c += 0.5;
        if (c < 0) {
            c = 0;
        } else if (c >= 1) {
            c = 1;
        }
        c = c * 255;
        //System.out.println("c: --> " + c);
        color[0] = (int) c;
        color[2] = 255 - (int) c;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
