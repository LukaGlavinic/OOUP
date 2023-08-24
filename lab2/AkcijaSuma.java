package lab2;

public class AkcijaSuma implements Akcija{
    private SlijedBrojeva slijed;

    public AkcijaSuma(SlijedBrojeva slijed) {
        this.slijed = slijed;
    }

    @Override
    public void akcija() {
        int sum = 0;
        for(int i : slijed.getCijeliBrojevi()) {
            sum += i;
        }
        System.out.println("Trenutna suma slijeda je: " + sum);
    }
}