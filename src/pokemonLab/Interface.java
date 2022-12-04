package pokemonLab;

import jdk.jshell.spi.ExecutionControl;

import java.util.*;

public class Interface {
    public static void main(String[] args) {
        Seel waterStarter = new Seel(35,12,5,1);
        Ponyta fireStarter = new Ponyta(30, 14, 5, 1);
        Bulbasaur leafStarter = new Bulbasaur(33, 16, 6, 1);

        Scanner input = new Scanner(System.in);
        Pokemon StarterPoke;


        STARTER: while (true) {
            System.out.print("Choose Your Starter: (L)eaf, (W)ater, (F)ire\n>>> ");
            String word = input.nextLine().toLowerCase();

            switch (word) {
                case "l", "leaf":
                    StarterPoke = leafStarter;
                    break STARTER;
                case "w", "water":
                    StarterPoke = waterStarter;
                    break STARTER;
                case "f", "fire":
                    StarterPoke = fireStarter;
                    break STARTER;
                default:
                    System.out.println("Not a Valid Pokemon");
            }
        }

        MAIN: while (true) {
            int minStat = (30 + (new Random().nextInt(0,5 )*StarterPoke.getLevel()));
            int maxStat = (35 + (new Random().nextInt(0,5 )*StarterPoke.getLevel()));
            String enemyType = new String[]{"Leaf", "Fire", "Water"}[new Random().nextInt(0,3)];
            Pokemon EnemyPokemon = new Pokemon(0,0,0,0,"NULL");
            switch (enemyType) {
                case "Leaf" -> EnemyPokemon = new Bulbasaur(new Random().nextInt(minStat,maxStat),
                        (int)(new Random().nextInt(minStat,maxStat+1)*(1/3f)),
                        (int)(new Random().nextInt(minStat,maxStat+1)*(1/4f)),
                        new Random().nextInt(StarterPoke.getLevel()-5, StarterPoke.getLevel())+4);
                case "Water" -> EnemyPokemon = new Seel(new Random().nextInt(minStat,maxStat),
                        (int)(new Random().nextInt(minStat,maxStat+1)*(1/3f)),
                        (int)(new Random().nextInt(minStat,maxStat+1)*(1/4f)),
                        Math.max(new Random().nextInt(StarterPoke.getLevel()-5,StarterPoke.getLevel()+4),1));
                case "Fire" -> EnemyPokemon = new Ponyta(new Random().nextInt(minStat,maxStat),
                        (int)(new Random().nextInt(minStat,maxStat+1)*(1/3f)),
                        (int)(new Random().nextInt(minStat,maxStat+1)*(1/4f)),
                        Math.max(new Random().nextInt(StarterPoke.getLevel()-5,StarterPoke.getLevel()+4),1));
            }

            BATTLE: while (true) {
                System.out.printf("YOU:%s%n  HEALTH:%s%n  ATTACK:%s%n  LEVEL:%s%nENEMY:%s%n  HEALTH:%s%n  ATTACK:%s%n  LEVEL:%s%n%n",
                        StarterPoke.getType(), StarterPoke.getHealth()[0], StarterPoke.getAttack()[0], StarterPoke.getLevel(),
                        EnemyPokemon.getType(), EnemyPokemon.getHealth()[0], EnemyPokemon.getAttack()[0], EnemyPokemon.getLevel());
                if (!StarterPoke.attackPokemon(EnemyPokemon)) {
                    System.out.print("(A)ttack or (R)un?\n>>> ");
                    String cmd = input.nextLine().toLowerCase();
                    switch (cmd) {
                        case "r", "run":
                            break BATTLE;
                    }
                }
                else {
                    if (StarterPoke.getHealth()[0] == 0) {
                        System.out.printf("Your %s Has Been Defeated! Game Over!%n", StarterPoke.getType());
                        System.exit(0);
                    }
                    else {
                        System.out.printf("Enemy %s Defeated!%n  NEW LEVEL:%s%n  NEW EXP:%s%n  EXP TO NEXT LEVEL:%s%n", EnemyPokemon.getType(), StarterPoke.getLevel(), StarterPoke.getExperience()[0], StarterPoke.getExperience()[1]);
                        break BATTLE;
                    }
                }
            }

            System.out.print("Fight New Enemy? (y/n)\n>>> ");
            String cmd = input.nextLine().toLowerCase();
            if ("n".equals(cmd)) {
                break;
            }

        }
    }
}




