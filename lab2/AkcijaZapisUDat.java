package lab2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;

public class AkcijaZapisUDat implements Akcija{

    private final File datoteka;
    private final SlijedBrojeva slijed;

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
                            new BufferedOutputStream(Files.newOutputStream(datoteka.toPath())), StandardCharsets.UTF_8));
            StringBuilder zapis = new StringBuilder();
            for(Integer i : slijed.getCijeliBrojevi()) {
                zapis.append(i);
                zapis.append(" ");
            }
            bw.write(zapis.toString());
            LocalDateTime currentDateTime = LocalDateTime.now();
            bw.write(currentDateTime.toString());
            bw.close();
        } catch (IOException e) {
            System.out.println("Javila se greska: " + e.getMessage());
        }
    }
}