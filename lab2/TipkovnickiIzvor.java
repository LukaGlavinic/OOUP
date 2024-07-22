package lab2;

import java.util.Scanner;

public class TipkovnickiIzvor implements IzvorBrojeva{

    private final Scanner skener;

    public TipkovnickiIzvor() {
        this.skener = new Scanner(System.in);
    }
    @Override
    public int next() {
        System.out.println("Upisi broj: ");
        int broj = skener.nextInt();
        if(broj == -1) {
            skener.close();
        }
        return broj;
    }
}