package lab2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sheet {

    private final Cell[][] poljaTablice;
    private int indexi;
    private int indexj;
    private Set<String> setPozivateljskih;

    public Sheet(int a, int b) {
        this.poljaTablice = new Cell[a][b];
        this.indexi = 0;
        this.indexj = 0;
        this.setPozivateljskih = new HashSet<>();
    }

    public Cell ref(String refString) {
        Cell polje = null;
        for (Cell[] cells : poljaTablice) {
            for (Cell cell : cells) {
                polje = cell;
                if (polje == null) {
                    return null;
                }
                if (polje.getIme().equals(refString)) {
                    return polje;
                }
            }
        }
        return polje;
    }

    public void set(String refString, String content) {
        Cell polje;
        boolean postoji = false;
        if(ref(refString) == null) {
            polje = new Cell(this);
        }else {
            polje = ref(refString);
            postoji = true;
        }
        double broj;
        polje.setIme(refString);
        polje.setExp(content);

        try {
            broj = Double.parseDouble(content);
        } catch (NumberFormatException e) {
            setPozivateljskih = new HashSet<>();
            broj = evaluate(polje);
        }
        polje.setValue(broj);
        
        if(postoji) {
            for(Promatrac p : polje.getListaPromatraca()) {
                p.update();
            }
        }
        
        List<Cell> zaPromatrati = getrefs(polje);
        if(!zaPromatrati.isEmpty()) {
            for(Cell c : zaPromatrati) {
                c.dodajPromatraca(new PromatracCelije(polje));
            }
        }
        
        if(!postoji) {
            poljaTablice[indexi][indexj] = polje;
        }
        
        if(!postoji) {
            if(indexj < poljaTablice[indexi].length - 1) {
                indexj++;
            }else {
                if(indexi < poljaTablice.length - 1) {
                    indexi++;
                }else {
                    throw new IndexOutOfBoundsException("kraj polja");
                }
                indexj = 0;
            }
        }
    }

    public List<Cell> getrefs(Cell cell) {
        List<Cell> listaPolja = new ArrayList<>();
        String expPolja = cell.getExp();
        String[] poljaZaDodat;
        if(expPolja.contains("+") || expPolja.contains("-") ||expPolja.contains("*") ||expPolja.contains("/")) {
            poljaZaDodat = expPolja.split("[+\\-*/]");
            for (String s : poljaZaDodat) {
                listaPolja.add(ref(s));
            }
        }else {
            try {
            Double.parseDouble(expPolja);
            } catch(NumberFormatException e) {
            listaPolja.add(ref(expPolja));
            }
        }
        return listaPolja;
    }

    public double evaluate(Cell cell) {
        double vrijednost = 0;
        List<Cell> referentne = getrefs(cell);
        if(referentne.isEmpty()) {
            setPozivateljskih.remove(cell.getIme());
            return cell.getValue();
        }else {
            setPozivateljskih.add(cell.getIme());
            for(Cell c : referentne) {
                if(setPozivateljskih.contains(c.getIme())) {
                    throw new RuntimeException("Kružna ovisnost");
                }
                vrijednost += c.getValue();
            }
        }
        return vrijednost;
    }

    public void print() {
        for (Cell[] cells : poljaTablice) {
            for (Cell tren : cells) {
                if (tren != null) {
                    System.out.println(tren.getIme() + ": " + tren.getExp() + " = " + tren.getValue());
                }
            }
        }
    }
}