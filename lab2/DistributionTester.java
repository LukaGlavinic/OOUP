package lab2;

import java.util.ArrayList;

public interface DistributionTester {
    
    ArrayList<Integer> generate(double a, double b, int c);

    int percentilRank(int p, ArrayList<Integer> listaBrojeva);

    int percentilInterp(int p, ArrayList<Integer> listaBrojeva);
}
