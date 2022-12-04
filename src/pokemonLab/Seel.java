package pokemonLab;

import java.util.*;

public class Seel extends Pokemon {
    public Seel(int health, int attack, int defense, int level) {
        super(health, attack, defense, level, "Water");
        super.effective.add("Fire");
        super.weak.add("Water");
        super.weak.add("Leaf");
    }
}
