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

        while (true) {
            int minStat = (30 + (new Random().nextInt(0,5 )*StarterPoke.getLevel()));
            int maxStat = (35 + (new Random().nextInt(0,5 )*StarterPoke.getLevel()));
            String enemyType = new String[]{"Leaf", "Fire", "Water"}[new Random().nextInt(0,3)];
            Pokemon EnemyPokemon = new Pokemon(0,0,0,0,"NULL");
            switch (enemyType) {
                case "Leaf" -> EnemyPokemon = new Bulbasaur(new Random().nextInt(minStat,maxStat),
                        new Random().nextInt(minStat,maxStat+1),
                        new Random().nextInt(minStat,maxStat+1),
                        new Random().nextInt(StarterPoke.getLevel()-5, StarterPoke.getLevel())+4);
                case "Water" -> EnemyPokemon = new Seel(new Random().nextInt(minStat,maxStat),
                        new Random().nextInt(minStat,maxStat+1),
                        new Random().nextInt(minStat,maxStat+1),
                        new Random().nextInt(StarterPoke.getLevel()-5,StarterPoke.getLevel()+4));
                case "Fire" -> EnemyPokemon = new Ponyta(new Random().nextInt(minStat,maxStat),
                        new Random().nextInt(minStat,maxStat+1),
                        new Random().nextInt(minStat,maxStat+1),
                        new Random().nextInt(StarterPoke.getLevel()-5,StarterPoke.getLevel()+4));
            }

            boolean f = true;
            while (f) {
                f = StarterPoke.attackPokemon(EnemyPokemon);
            }
            break;
        }
    }
}




