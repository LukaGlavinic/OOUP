package model.plugins;

import model.Animal;

public class Parrot extends Animal{

    private final String animalName;

	public Parrot(String name) {
		this.animalName = name;
	}

    @Override
    public String greet() {
        return "Kaakaaa";
    }

    @Override
    public String menu() {
        return "Nuts";
    }

    @Override
    public String name() {
        return animalName;
    }
}