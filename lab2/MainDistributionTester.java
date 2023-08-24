package lab2;

import java.util.ArrayList;

public class MainDistributionTester {

    public static void main(String[] args) {

        ArrayList<Integer> listaB = new ArrayList<>();

        DistributionTester tester = new SequentialTester();
        listaB = tester.generate(0, 20, 4);
        System.out.println(listaB.toString());
        System.out.println(tester.percentilRank(80, listaB));
        System.out.println(tester.percentilInterp(80, listaB));

        tester = new RandomTester();
        listaB = tester.generate(2, 3, 20);

        System.out.println(listaB.toString());
        System.out.println(tester.percentilRank(80, listaB));
        System.out.println(tester.percentilInterp(80, listaB));

        tester = new FibonacciTester();
        listaB = tester.generate(2, 1, 10);

        System.out.println(listaB.toString());
        System.out.println(tester.percentilRank(80, listaB));
        System.out.println(tester.percentilInterp(80, listaB));
    }
}