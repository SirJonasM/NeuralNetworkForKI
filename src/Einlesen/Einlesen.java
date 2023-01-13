package Einlesen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Einlesen {
    public static double[][] einlesen(String s,int attributes,String[] clazzes, int clazzId) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(s));
        Map<String,Integer> clazzesMap = new HashMap<>();
        for (int i = 0;i< clazzes.length;i++){
            clazzesMap.put(clazzes[i],i);
        }
        int muster = 0;
        double[] maximas = new double[attributes];

        while (scanner.hasNext()){
            for(int i = 0;i<attributes+1;i++) {
                double value = Double.parseDouble(scanner.next());
                if (i == clazzId) {
                    continue;
                }
                if (maximas[i] < value) maximas[i] = value;
            }
            muster++;
        }

        double[][] inputs = new double[muster][attributes+clazzes.length];
        scanner = new Scanner(new File(s));
        for(int m = 0;m<muster;m++){
            for(int i = 0;i<attributes+1;i++){
                if (i == clazzId){
                    int clazz = clazzesMap.get(scanner.next());
                    for(int n = 0;n<clazzes.length;n++){
                        if(n == clazz) inputs[m][i+n] = 1;
                        else inputs[m][n] = 0;
                    }
                    continue;
                }
                double value = Double.parseDouble(scanner.next());
                inputs[m][i] = value;
            }
        }
        return inputs;

    }
}
