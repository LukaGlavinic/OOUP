package lab2;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.time.LocalDateTime;

public class AkcijaZapisUDat implements Akcija{

    private File datoteka;
    private SlijedBrojeva slijed;

    public AkcijaZapisUDat(String putanja, SlijedBrojeva slijed) {
        this.datoteka = new File(putanja);
        this.slijed = slijed;
    }

    @Override
    public void akcija() {
        try {
            FileWriter writer = new FileWriter(datoteka);
            writer.write("");
            writer.close();
            Writer bw = new BufferedWriter(
                            new OutputStreamWriter(
                            new BufferedOutputStream(Files.newOutputStream(datoteka.toPath())),"UTF-8"));
            String zapis = "";
            for(Integer i : slijed.getCijeliBrojevi()) {
                zapis += i;
                zapis += " ";
            }
            bw.write(zapis);
            LocalDateTime currentDateTime = LocalDateTime.now();
            bw.write(currentDateTime.toString());
            bw.close();
        } catch (IOException e) {
            System.out.println("Javila se greska: " + e.getMessage());
        }
    }
}