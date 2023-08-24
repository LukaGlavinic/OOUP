package lab2;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private String ime;
    private String exp;
    private double value;
    private Sheet tablica;
    private List<Promatrac> listaPromatraca;

    public Cell(Sheet tablica) {
        this.tablica = tablica;
        this.listaPromatraca = new ArrayList<>();
    }

    public List<Promatrac> getListaPromatraca() {
        return listaPromatraca;
    }

    public void dodajPromatraca(Promatrac p) {
        listaPromatraca.add(p);
    }

    public String getIme() {
        return ime;
    }

    public Sheet getTablica() {
        return tablica;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getExp() {
        return exp;
    }

    public double getValue() {
        return value;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public void setValue(double value) {
        this.value = value;
    }
}