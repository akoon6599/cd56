package recursivePassword;

import java.util.Scanner;
import java.util.random.RandomGenerator;

public class Interface {
    private static final StringBuilder NewPassword = new StringBuilder();
    private static final int CharWeight = 55;
    private static final int NumWeight = 27;
    private static final int SymbolWeight = 13;
    private static final char[][] SymbolList = {
            {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},
            {'1','2','3','4','5','6','7','8','9'},
            {'`',',','.','/','<','>','?','-','=','~','_','+','!','@','#','$','%','^','&','*','(',')'}
    };
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Length of Password: ");
        int length = scanner.nextInt();
        scanner.nextLine();
        System.out.print("\nAdd Base String (y/N)? ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("y")) {
            System.out.print("\nProvide a Base String: ");
            String baseString = scanner.nextLine();
            genPassword(length, baseString);
            System.out.printf("%nGenerated Password: %s",NewPassword);
        }
        else {
            genPassword(length);
            System.out.printf("%nGenerated Password: %s",NewPassword);
        }
        scanner.close();
        System.exit(1);
    }

    private static void genPassword(int length) {
        int listChoice = RandomGenerator.getDefault().nextInt(0,100);
        if (listChoice < CharWeight) {
            NewPassword.append(SymbolList[0][RandomGenerator.getDefault().nextInt(0, SymbolList[0].length)]);
        } else if (listChoice < CharWeight+NumWeight) {
            NewPassword.append(SymbolList[1][RandomGenerator.getDefault().nextInt(0, SymbolList[1].length)]);
        }
        else if (listChoice < CharWeight+NumWeight+SymbolWeight){
            NewPassword.append(SymbolList[2][RandomGenerator.getDefault().nextInt(0, SymbolList[2].length)]);
        }

        if (NewPassword.length() < length) {
            genPassword(length);
        }
    }
    private static void genPassword(int length, String base) {
        NewPassword.append(base);
        int listChoice = RandomGenerator.getDefault().nextInt(0,100);
        if (listChoice < CharWeight) {
            NewPassword.append(SymbolList[0][RandomGenerator.getDefault().nextInt(0, SymbolList[0].length)]);
        } else if (listChoice < CharWeight+NumWeight) {
            NewPassword.append(SymbolList[1][RandomGenerator.getDefault().nextInt(0, SymbolList[1].length)]);
        }
        else if (listChoice < CharWeight+NumWeight+SymbolWeight){
            NewPassword.append(SymbolList[2][RandomGenerator.getDefault().nextInt(0, SymbolList[2].length)]);
        }

        if (NewPassword.length() < length) {
            genPassword(length);
        }
    }
}
