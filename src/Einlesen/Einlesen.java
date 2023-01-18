package Einlesen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Einlesen {
    public static double[] maximas;
    public static boolean maximasSet = false;

    public static double[][] einlesen(String s,int attributes,String[] clazzes, int clazzId) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(s));
        Map<String,Integer> clazzesMap = new HashMap<>();
        for (int i = 0;i< clazzes.length;i++){
            clazzesMap.put(clazzes[i],i);
        }
        int muster = 0;
        if(!maximasSet)
        maximas = new double[attributes];

        while (scanner.hasNext()){
            for(int i = 0;i<attributes+1;i++) {
                if (i == clazzId) {
                    scanner.next();
                    continue;
                }
                double value = Double.parseDouble(scanner.next());
                if(maximasSet) continue;
                if (maximas[i] < value) maximas[i] = value;
            }
            muster++;
        }
        maximasSet = true;

        double[][] inputs = new double[muster][attributes+clazzes.length];
        scanner = new Scanner(new File(s));
        for(int m = 0;m<muster;m++){
            for(int i = 0;i<attributes+1;i++){
                if (i == clazzId){
                    String st = scanner.next();
                    int clazz = clazzesMap.get(st);
                    for(int n = 0;n<clazzes.length;n++){
                        if(n == clazz) inputs[m][i+n] = 1;
                        else inputs[m][n+i] = 0;
                    }
                    continue;
                }
                double value = Double.parseDouble(scanner.next());
                inputs[m][i] = value/maximas[1];
            }
        }
        return inputs;
    }

    public static double[][] einlesenWetter(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));
        int patterns = 0;
        double max1 = 100.0;
        double max2 = 100.0;
        while (scanner.hasNext()){
            double v1 = Double.parseDouble(scanner.next());
            double v2 = Double.parseDouble(scanner.next());

            if (max1<v1) max1 = v1;
            if (max2<v2) max2 = v2;

            patterns++;
        }
        double[][] inputs = new double[patterns][3];
        scanner = new Scanner(new File(file));

        int i = 0;
        while(scanner.hasNext()){
            double v1 = Double.parseDouble(scanner.next())/max1;
            double v2 = Double.parseDouble(scanner.next())/max2;

            inputs[i][0] = v1;
            inputs[i][1] = v2;

            double clazz = Double.parseDouble(scanner.next());
            inputs[i][2] = clazz;
            i++;
        }
        return inputs;

    }
}
