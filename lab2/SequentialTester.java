package lab2;

import java.util.ArrayList;

public class SequentialTester implements DistributionTester{
 
    @Override
    public ArrayList<Integer> generate(double a, double b, int c) {
        ArrayList<Integer> lista = new ArrayList<>();
        for(double i = a; i < b; i += c) {
            lista.add((int)Math.round(i));
        }
        return lista;
    }
}