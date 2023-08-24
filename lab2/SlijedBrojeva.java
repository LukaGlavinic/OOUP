package lab2;

import java.util.ArrayList;

public class SlijedBrojeva {
    private ArrayList<Integer> cijeliBrojevi;
    private IzvorBrojeva izvor;
    private ArrayList<Akcija> akcije;

    public SlijedBrojeva(IzvorBrojeva izvor) {
        this.cijeliBrojevi = new ArrayList<>();
        this.izvor = izvor;
        this.akcije = new ArrayList<>();
    }

    public void dodajAkciju(Akcija a) {
        akcije.add(a);
    }

    public void odjaviAkciju(Akcija a) {
        akcije.remove(a);
    }

    public void obavjesti() {
        for(Akcija a : akcije) {
            a.akcija();
        }
    }

    public void kreni() {
        try {
            Integer broj = izvor.next();
            while(broj != -1) {
                cijeliBrojevi.add(broj);
                obavjesti();
                Thread.sleep(1000);
                broj = izvor.next();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Integer> getCijeliBrojevi() {
        return cijeliBrojevi;
    }
}