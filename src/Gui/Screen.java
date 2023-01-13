package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Screen extends JPanel implements Runnable, KeyListener, MouseListener {


    final int screenWidth = 1000;
    final int screenHeight = 700;

    public static double f = 0;
    public static int time = 0;

    int FPS = 40;


    Thread myThread;
    private JPanel MainPanel;

    public Screen() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        //this.setBackground(Color.black);
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

            double drawInterval = 1000000000 / FPS; // 0.01666 seconds
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
        Random rand = new Random();
        //Graphics g2 = (Graphics2D) g;
        //g.drawOval(10, 10, 10, 10);
        //drawNeurons(10, 200, g);
        //drawNeurons(7, 300, g);
        int[] neuronsPerColumn = {10, 7, 5, 15, 3, 1};
        drawAllNeurons(neuronsPerColumn, g);
    }

    public void drawAllNeurons(int[] neuronsPerColumn, Graphics g){
        int xPos = (screenWidth)/neuronsPerColumn.length;
        for(int i = 0; i<neuronsPerColumn.length; i++){
            drawNeurons(neuronsPerColumn[i], (xPos*i) + xPos, g);
        }
    }

    public void drawNeurons(int n, int xPos, Graphics g){
        if(n == 0){
            return;
        }
        int yPos = (screenHeight)/n;

        for(int i = 0; i<n; i++){
            g.drawOval(xPos, yPos + (i*yPos), 10, 10);
        }
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
