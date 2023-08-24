package lab2;

public class MainTestSheet {
    public static void main(String[] args) {
        Sheet s = new Sheet(5, 5);
        try {
            s.set("A1", "2");
            s.set("A2", "5");
            s.set("A3", "A1+A2");
            s.print();

            s.set("A1", "4");
            s.set("A4", "A1+A3");
            s.print();

            s.set("A5", "A4+A1");
            s.set("A3", "A4");
            // s.set("A1", "A3");
            // s.set("A1", "A4");
            // s.set("A4", "A3");
            // s.set("A3", "A1");
            s.print();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("Izlazak");
            System.exit(0);
        }
    }
}