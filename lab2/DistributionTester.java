package lab2;

import java.util.ArrayList;
import java.util.Collections;

public interface DistributionTester {
    
    ArrayList<Integer> generate(double a, double b, int c);

    default int percentilRank(int p, ArrayList<Integer> listaBrojeva) {
        int N = listaBrojeva.size();
    	Collections.sort(listaBrojeva);
        int np = (int) Math.round(p * N / 100 + 0.5);
        return np > N ? listaBrojeva.get(N - 1) : listaBrojeva.get(np - 1);
    }

    default int percentilInterp(int p, ArrayList<Integer> listaBrojeva) {
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