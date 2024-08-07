package lab2;

public class PromatracCelije implements Promatrac{

    private final Cell vlastita;

    public PromatracCelije(Cell vlastita) {
        this.vlastita = vlastita;
    }
    @Override
    public void update() {
        vlastita.setValue(vlastita.getTablica().evaluate(vlastita));
    }
}