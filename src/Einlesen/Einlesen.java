package Einlesen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Einlesen {

    public static double[][] einlesen(String s) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(s));
        int muster = 0;
        int attributes = 2;
        int clazzes = 1;
        double max1 = 0;
        double max2 = 0;
        double max3 = 0;
        double max4 = 0;

        while (scanner.hasNext()){
            double i1 = Double.parseDouble(scanner.next());
            double i2 = Double.parseDouble(scanner.next());
//            double i3 = Double.parseDouble(scanner.next(
//            double i4 = Double.parseDouble(scanner.next());
            String clazz = scanner.next();
            if (max1< i1)max1 = i1;
            if (max2< i2)max2 = i2;
//            if (max3< i3)max3 = i3;
//            if (max4< i4)max4 = i4;
            muster++;
        }
        double[][] inputs = new double[muster][attributes+clazzes];
        scanner = new Scanner(new File(s));
        for(int m = 0;m<muster;m++){
            inputs[m][0] = Double.parseDouble(scanner.next())/max1;
            inputs[m][1] = Double.parseDouble(scanner.next())/max2;
//            inputs[m][2] = Double.parseDouble(scanner.next())/max3;
//            inputs[m][3] = Double.parseDouble(scanner.next())/max4;
            double clazz = Double.parseDouble(scanner.next());
            inputs[m][2] = clazz;

//            if (clazz == 0) inputs[m][4] = 1;
//            if (clazz == 1) inputs[m][5] = 1;
//            if (clazz == 2) inputs[m][6] = 1;
        }
        return inputs;

    }
}
