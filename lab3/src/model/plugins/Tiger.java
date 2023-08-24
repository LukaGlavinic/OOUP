package model.plugins;

import model.Animal;

public class Tiger extends Animal{

    private String animalName;

	public Tiger(String name) {
		this.animalName = name;
	}

    @Override
    public String greet() {
        return "Roarrr";
    }

    @Override
    public String menu() {
        return "Meat";
    }

    @Override
    public String name() {
        return animalName;
    }
}