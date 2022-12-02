package pokemonLab;

public class Bulbasaur extends Pokemon{
    String[] effective = {"Water"};
    String[] weak = {"Leaf", "Fire"};
    public Bulbasaur(int health, int attack, int defense, int level) {
        super(health, attack, defense, level, "Leaf");
        super.effective.add("Water");
        super.weak.add("Leaf");
        super.weak.add("Fire");
    }
}
