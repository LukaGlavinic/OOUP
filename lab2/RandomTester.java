package lab2;

import java.util.ArrayList;
import java.util.Random;

public class RandomTester implements DistributionTester{

    @Override
    public ArrayList<Integer> generate(double a, double b, int c) {
        ArrayList<Integer> lista = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < c; i++) {
            int nekiBroj = (int) Math.round(a + b * random.nextGaussian());
            lista.add(nekiBroj);
        }
        return lista;
    }
}