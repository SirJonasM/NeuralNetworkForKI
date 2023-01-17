package Gui;
import org.w3c.dom.css.RGBColor;

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
            if (value>0) value *= maxWert;
            else value *=minWert;
            int red = 0;
            int blue = 0;
            red = (int) (128 + 127 * value);
            blue = (int) (128 - 127 * value);
            int green = 0;
            Color color = new Color(red, green, blue);
            image.setRGB(x,y,color.getRGB());
        }
    }

    try {
        ImageIO.write(image, "png", new File("image.png"));
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
