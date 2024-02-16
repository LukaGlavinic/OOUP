package model;

import java.lang.reflect.Constructor;

public class AnimalFactory {
    
    @SuppressWarnings("unchecked")
    public static Animal newInstance(String animalKind, String name) {
        // ClassLoader parent = AnimalFactory.class.getClassLoader();
        // URLClassLoader newClassLoader = new URLClassLoader(
	    //     new URL[] {
        //     // Dodaj jedan direktorij (završava s /)
        //     new File("D:/java/plugins/").toURI().toURL(),
        //     // Dodaj jedan konkretan JAR (ne završava s /)
        //     new File("D:/java/plugins-jarovi/zivotinje.jar").toURI().toURL()
	    //     }, parent);

        Class<Animal> clazz = null;
        try {
            clazz = (Class<Animal>)Class.forName("model.plugins." + animalKind);
            Constructor<?> ctr = clazz.getConstructor(String.class);
            return (Animal)ctr.newInstance(name);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        Animal papiga = newInstance("Parrot", "Pero");

        assert papiga != null;
        System.out.println("Papiga se zove: " + papiga.name());
        papiga.animalPrintGreeting();
        papiga.animalPrintMenu();

        Animal tigar = newInstance("Tiger", "Tibor");

        assert tigar != null;
        System.out.println("Tigar se zove: " + tigar.name());
        tigar.animalPrintGreeting();
        tigar.animalPrintMenu();
    }
}