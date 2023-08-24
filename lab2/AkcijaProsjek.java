package lab2;

public class AkcijaProsjek implements Akcija{
    private SlijedBrojeva slijed;

    public AkcijaProsjek(SlijedBrojeva slijed) {
        this.slijed = slijed;
    }

    @Override
    public void akcija() {
        int sum = 0;
        for(int i : slijed.getCijeliBrojevi()) {
            sum += i;
        }
        System.out.println("Trenutni prosjek slijeda je: " + sum / slijed.getCijeliBrojevi().size());
    }
}