package pokemonLab;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

public class Pokemon {
    ArrayList<String> effective = new ArrayList<>();
    ArrayList<String> weak = new ArrayList<>();
    private int[] health; // current health - base health - max health
    private int[] attack; // current attack - base attack
    private int[] defense; // current defense - base defense
    private final String _type;
    private int level;
    private int[] experience = {0,15,30}; // current xp - xp for next level - base xp dropped when killed (before formula)

    public Pokemon(int hp, int att, int def, int lev, String type) {
        this.health = new int[]{hp, hp, hp};
        this.attack = new int[]{att, att};
        this.defense = new int[]{def, def};
        this.level = lev;
        this._type = type;
    }

    public void levelUp() {
        while (experience[0] >= experience[1]) {
            health[2] += new int[]{0, 1, 1, 1, 1, 1}[new Random().nextInt(0, 6)] * (Math.floor(new Random().nextDouble(1.2f, 2.3f) * 0.3 * (level) * 0.15 * health[1]));
            attack[0] += new int[]{0, 0, 1, 1, 1, 1}[new Random().nextInt(0, 6)] * (Math.floor(new Random().nextDouble(1.2f, 2.7f) * 0.3 * (level) * 0.15 * attack[1]));
            defense[0] += new int[]{0, 0, 1, 1, 1, 1}[new Random().nextInt(0, 6)] * (Math.floor(new Random().nextDouble(1.2f, 2.4f) * 0.3 * (level) * 0.15 * defense[1]));
            level += 1;
            experience[0] -= experience[1];
            experience[1] *= 1.5;
        }
        health[0] = health[2];
    }

    public boolean attackPokemon(Pokemon otherPoke) {
        double offMult = (effective.contains(otherPoke.getType()))?2:( (weak.contains(otherPoke.getType()))?0.5:1f );
        double defMult = (otherPoke.effective.contains(_type))?2:( (otherPoke.weak.contains(_type))?0.5:1f );
        int yourDmg = (int)Math.max((attack[0] - otherPoke.getDefense()[0])*offMult, 0);
        otherPoke.setHealth(Math.max(otherPoke.getHealth()[0] - yourDmg,0));
        System.out.printf("Your %s Has Dealt %s Damage! Enemy %s Has %s Health Remaining.%n", _type, yourDmg, otherPoke.getType(), otherPoke.getHealth()[0]);

        if (otherPoke.getHealth()[0] <= 0) {
            experience[0] += (otherPoke.getExperience()[2]*(otherPoke.getLevel() / level)) + level;
            levelUp();

            return true;
        }
        else {
            int enemDmg = (int)Math.max((otherPoke.getAttack()[0] - defense[0])*defMult, 0);
            health[0] = Math.max(health[0] - enemDmg, 0);
            System.out.printf("Enemy %s Has Dealt %s Damage! Your %s Has %s Health Remaining.%n", otherPoke.getType(), enemDmg, _type, health[0]);

            if (health[0] <= 0) {
                otherPoke.setExperience(otherPoke.getExperience()[0] + (experience[2]*(level / otherPoke.getLevel())) + level);
                otherPoke.levelUp();

                return true;
            }
        }

        return false;
    }


    public int[] getHealth() {
        return health;
    }
    public int[] getAttack() {
        return attack;
    }
    public int[] getDefense() {
        return defense;
    }
    public int[] getExperience() {
        return experience;
    }
    public int getLevel() {
        return level;
    }
    public String getType() {
        return _type;
    }
    public void setHealth(int newHealth) {
        health[0] = newHealth;
    }
    public void setExperience(int newExp) {
        experience[0] = newExp;
    }
}

