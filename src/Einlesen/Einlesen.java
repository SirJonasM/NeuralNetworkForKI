package Einlesen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Einlesen {

    public static double[][] einlesen(String s,int[] attributes,String[] clazzes, int clazzId) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(s));
        boolean isNumeric = clazzes[0].chars().allMatch(Character::isDigit);
        int muster = 0;
        double[] maximas = new double[attributes.length];

        while (scanner.hasNext()){
            for(int i = 0;i<attributes.length+1;i++) {
                double value = Double.parseDouble(scanner.next());
                if (i == clazzId) {
                    continue;
                }
                if (maximas[i] < value) maximas[i] = value;
            }
            muster++;
        }

        double[][] inputs = new double[muster][attributes.length+clazzes.length];
        scanner = new Scanner(new File(s));
        for(int m = 0;m<muster;m++){
            for(int i = 0;i<attributes.length+1;i++){
                double value = Double.parseDouble(scanner.next());
                if (i >= clazzId || i<clazzId+clazzes.length){
                    continue;
                }
                inputs[m][i] = value;
            }
        }
        return inputs;

    }
}
