package lab2;

import java.util.ArrayList;
import java.util.Collections;

public class FibonacciTester implements DistributionTester{

    public FibonacciTester() {
        super();
    }
    
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

    @Override
    public int percentilRank(int p, ArrayList<Integer> lista) {
        int np = (int) Math.ceil(p * lista.size() / 100);
        return lista.get(np - 1);
    }

    @Override
    public int percentilInterp(int p, ArrayList<Integer> listaBrojeva) {
        Collections.sort(listaBrojeva);
        int v_i = listaBrojeva.get(0), v_i1 = 0;
        int N = listaBrojeva.size();
        double p_v_i = 0, p_v_i1 = 0;
        for(int i = 1; i <= N; i++) {
            v_i = listaBrojeva.get(i - 1);
            p_v_i = 100 * (i - 0.5) / N;
            if(p < p_v_i && i == 1 || p > p_v_i && i == N) {
                return v_i;
            }
            v_i1 = listaBrojeva.get(i);
            p_v_i1 = 100 * (i + 0.5) / N;
            if(p_v_i < p && p < p_v_i1) {
                break;
            }
        }
        return (int) Math.round(v_i + N * (p - p_v_i) * (v_i1 - v_i) / 100);
    }
}
