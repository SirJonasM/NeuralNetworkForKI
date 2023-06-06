package Gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
public class ArrayToImage {
    public static void createImage(double[][] data,double maxWert,double minWert) {
    int width = data.length;
    int height = data[0].length;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            double value = data[x][y];

            value = func(value,100);

            int red = (int) (255 * value);
            int blue = (int) (255 * (1-value));
            int green = 0;
            Color color = new Color(red, green, blue);
            image.setRGB(x,y,color.getRGB());
        }
    }

    try {
        ImageIO.write(image, "png", new File("Wetter.png"));
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//    Red: Sinus
//    Green: Exponential
//    Blue: Identity
    public static void createImage(double[][][] data, double[] maxWert, double[] minWert) {
        int width = data[0].length;
        int height = data.length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double value1 = data[y][x][0]/2;
                double value2 = data[y][x][1];
                double value3 = data[y][x][2];


                value1 = func(value1,10);

                value2 = func(value2,10);

                value3 = func(value3,10);

                int red = (int) (255 * value1);
                int green = (int) (255 * value2);
                int blue = (int) (255*  value3);

                Color color = new Color(red, green, blue);
                image.setRGB(x,y,color.getRGB());
            }
        }
        try {
            ImageIO.write(image, "png", new File("sinExId.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double func(double value,double factor) {
        value =  ((Math.atan(factor*value)*2/Math.PI)+1)/2.0;
        return value;
    }
}
