package recursiveArrayChecker;

import java.util.Arrays;
import java.util.Scanner;
import java.util.random.RandomGenerator;

public class Interface {
    public static void main(String[] args) {
        int[] array = new int[RandomGenerator.getDefault().nextInt(0,10)];
        for (int i=0;i<array.length;i++) {
            if (array[i]==0) {array[i] = RandomGenerator.getDefault().nextInt(0,32768);}
        }

        System.out.println(Arrays.toString(array));

        Scanner input = new Scanner(System.in);
        System.out.print("Minimum Limit Number: ");
        int lowerLimit = input.nextInt();
        input.nextLine();
        try {
            System.out.printf("Index of First Value Lower Than {%d}: Position %d", lowerLimit, checkArray(array, lowerLimit, 0));
        }
        catch (StackOverflowError | ArrayIndexOutOfBoundsException error) {
            System.out.printf("No Values In Array Lower Than {%d}",lowerLimit);
        }
    }

    public static int checkArray(int[] array, int lowerLimit, int index) {
        if (array[index] < lowerLimit) {return index;}
        else {return checkArray(array, lowerLimit, index+1);}
    }
}
