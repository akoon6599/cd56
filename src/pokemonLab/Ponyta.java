package pokemonLab;

public class Ponyta extends Pokemon {
    public Ponyta(int health, int attack, int defense, int level) {
        super(health, attack, defense, level, "Fire");
        super.effective.add("Leaf");
        super.weak.add("Water");
        super.weak.add("Fire");
    }
}
