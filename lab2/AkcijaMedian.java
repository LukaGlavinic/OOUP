package lab2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AkcijaMedian implements Akcija{
    private SlijedBrojeva slijed;

    public AkcijaMedian(SlijedBrojeva slijed) {
        this.slijed = slijed;
    }

    @Override
    public void akcija() {
        int median;
        List<Integer> kopija = new ArrayList<>(slijed.getCijeliBrojevi().size());
        for(int i = 0; i < slijed.getCijeliBrojevi().size(); i++) {
            kopija.add(slijed.getCijeliBrojevi().get(i));
        }
        Collections.sort(kopija);
        int N = kopija.size();
        if(N % 2 == 1) {
            median = kopija.get((N + 1) / 2 - 1);
        }else {
            median = Math.round((kopija.get(N / 2 - 1) + kopija.get(N / 2)) / 2);
        }
        System.out.println("Trenutni median slijeda je: " + median);
    }
}