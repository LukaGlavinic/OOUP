package lab2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

public class DatotecniIzvor implements IzvorBrojeva{

    private File datoteka;
    private List<String> linije;
    private Iterator<String> iter;

    public DatotecniIzvor(String putanja) {
        this.datoteka = new File(putanja);
        try {
            this.linije = Files.readAllLines(datoteka.toPath());
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
        this.iter = linije.iterator();
    }
    @Override
    public int next() {
        if(iter.hasNext()) {
            return Integer.parseInt(iter.next());
        }else {
            return -1;
        }
    }
}