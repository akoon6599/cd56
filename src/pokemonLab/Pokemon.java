package pokemonLab;

import jdk.jshell.spi.ExecutionControl;

public class Pokemon {
    protected int health;
    protected int attack;
    protected int defense;
    protected String _type;
    protected int level;
    protected int[] experience = {0,15,30}; // current xp - xp for next level - base xp dropped when killed (before formula)

    public Pokemon(int hp, int att, int def, int lev) {
        this.health = hp;
        this.attack = att;
        this.defense = def;
        this.level = lev;
    }

    public void levelUp(Pokemon otherPokemon) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Level Up Not Implemented");
    }

    public int getHealth() {
        return health;
    }
    public int getAttack() {
        return attack;
    }
    public int getDefense() {
        return defense;
    }
    public int getExperience() {
        return experience[0];
    }
    public int getLevel() {
        return level;
    }
}

