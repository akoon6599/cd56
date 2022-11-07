package pick6;

import java.util.ArrayList;
import java.util.*;

public class pick6 {
    public static void main(String[] args) {
        Integer numberTickets = 100000;
        int len = 6;

        Ticket winningTicket = new Ticket(len); // create winning ticket
        long Balance = 0;
        for (int i=0; i<numberTickets; i++) { // loop for x times, subtract 2 for each ticket then add winning number
            Balance -= 2;
            Ticket lotto = new Ticket(len);
            Balance += lotto.checkEquals(winningTicket);
            if (numberTickets > 100000 && (i%10000000)==0) { // for tracking larger numbers of tickets, cuz why not
                System.out.printf("Ticket Number: %d%n", i);
                System.out.printf("Current Balance: %d%n%n", Balance);
            }
        }
        System.out.printf("Your final balance is: %d%n", Balance);
        System.out.printf("You won: %d%n", numberTickets*2 + Balance);
        System.out.printf("Your ROI: %.3f%n", (Balance)/((numberTickets.floatValue())*2));
    }
}

class Ticket {
    ArrayList<Integer> Number;
    int Length;

    public Ticket(int len) { // Create random numbers of `len` length
        this.Length = len;
        this.Number = new ArrayList<>(len);
        for (int i=0; i<len; i++) {this.Number.add(new Random().nextInt(1, 100));}
    }
    public long checkEquals(Ticket reference) {
        int i=0;
        int matches=0;
        for (Integer in : this.Number) { // check if the ticket matches winner
            if (Objects.equals(reference.Number.get(i), in)) {matches++;}
            i++;
        }
        // return the proper rewards for each # matches
         if (matches==this.Length-5) {return 4;}
        else if (matches==this.Length-4) {return 7;}
        else if (matches==this.Length-3) {return 100;}
        else if (matches==this.Length-2) {return 50000;}
        else if (matches==this.Length-1) {return 1000000;}
        else if (matches==this.Length) {
            System.out.println("Congrats! You got the winning ticket!");
            return 25000000;
        }
        else {return 0;}
    }
}
