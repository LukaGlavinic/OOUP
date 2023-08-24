package lab2;

import java.util.ArrayList;

public class FibonacciTester implements DistributionTester{
    
    @Override
    public ArrayList<Integer> generate(double a, double b, int c) {
        ArrayList<Integer> lista = new ArrayList<>();
        int prvi = 0, drugi = 1;
        for(int i = 0; i < c; i++) {
            int tren = prvi + drugi;
            lista.add(tren);
            prvi = drugi;
            drugi = tren;
        }
        return lista;
    }
}