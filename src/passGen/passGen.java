package passGen;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class passGen {
    static final String CHARACTER_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&\\'()*+,-./:;<=>?@[\\\\]^_`{|}~";
    static final String STRCHARACTER_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String NUMCHARACTER_POOL = "0123456789";
    static final String SPCCHARACTER_POOL = "!\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Desired Length of Password:\n>>> ");
        int length = input.nextInt();
        System.out.print("Minimum Desired Number of Letters:\n>>> ");
        int numLet = input.nextInt();
        System.out.print("Minimum Desired Number of Numbers:\n>>> ");
        int numNum = input.nextInt();
        System.out.print("Minimum Desired Number of Special Characters:\n>>> ");
        int numSpc = input.nextInt();
        int numRnd = (numLet+numNum+numSpc)<length?length-(numLet+numNum+numSpc):0;

        ArrayList<String> passwordBuilder = new ArrayList<>(length);
        for (int i=0;i<length;i++) {passwordBuilder.add(" ");}

        for (int i=0;i<numLet;i++) {
            Random random = new Random();
            int arrayPos = random.nextInt(length);
            String character = ""+STRCHARACTER_POOL.charAt(random.nextInt(STRCHARACTER_POOL.length()));
            boolean newPos = false;
            while (!newPos) {
                if (passwordBuilder.get(arrayPos).equals(" ")) {
                    passwordBuilder.set(arrayPos, character);
                    newPos = true;
                }
                else {
                    arrayPos = random.nextInt(length);
                }
            }
        }
        for (int i=0;i<numNum;i++) {
            Random random = new Random();
            int arrayPos = random.nextInt(length);
            String character = ""+NUMCHARACTER_POOL.charAt(random.nextInt(NUMCHARACTER_POOL.length()));
            boolean newPos = false;
            while (!newPos) {
                if (passwordBuilder.get(arrayPos).equals(" ")) {
                    passwordBuilder.set(arrayPos, character);
                    newPos = true;
                }
                else {
                    arrayPos = random.nextInt(length);
                }
            }
        }
        for (int i=0;i<numSpc;i++) {
            Random random = new Random();
            int arrayPos = random.nextInt(length);
            String character = ""+SPCCHARACTER_POOL.charAt(random.nextInt(SPCCHARACTER_POOL.length()));
            boolean newPos = false;
            while (!newPos) {
                if (passwordBuilder.get(arrayPos).equals(" ")) {
                    passwordBuilder.set(arrayPos, character);
                    newPos = true;
                }
                else {
                    arrayPos = random.nextInt(length);
                }
            }
        }
        for (int i=0;i<numRnd;i++) {
            Random random = new Random();
            int arrayPos = random.nextInt(length);
            String character = ""+CHARACTER_POOL.charAt(random.nextInt(CHARACTER_POOL.length()));
            boolean newPos = false;
            while (!newPos) {
                if (passwordBuilder.get(arrayPos).equals(" ")) {
                    passwordBuilder.set(arrayPos, character);
                    newPos = true;
                }
                else {
                    arrayPos = random.nextInt(length);
                }
            }
        }
        StringBuilder finalPassword = new StringBuilder();
        for (String c : passwordBuilder) {
            finalPassword.append(c);
        }

        System.out.printf("\nGenerated Password:\n%s\n\n",finalPassword);
    }
}
