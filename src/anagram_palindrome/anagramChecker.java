package anagram_palindrome;

import java.util.Scanner;

public class anagramChecker {
    public static void main(String[] args) {
        String HELP = """
                > (a)anagram (firstWord, secondWord): check if secondWord is an anagram of firstWord
                > (p)alindrome (Word): check if Word is a palindrome
                > (q)uit (): exit program""";
        System.out.println(HELP);
        Scanner scan = new Scanner(System.in);
        END:
        while (true) {
            System.out.print(">>> ");
            String cmd = scan.next().toLowerCase();

            switch (cmd) {
                case "a":
                case "anagram":
                    String word1 = scan.next(), word2 = scan.next();
                    if (checkAnagram(word1,word2)) {
                        System.out.printf("`%s` And `%s` Are Anagrams%n",word1,word2);
                    }
                    else {System.out.printf("`%s` And `%s` Are Not Anagrams%n",word1,word2);}
                    break;
                case "p":
                case "palindrome":
                    String word = scan.next();
                    if (checkPalindrome(word)) {
                        System.out.printf("`%s` Is A Palindrome%n",word);
                    }
                    else {System.out.printf("`%s` Is Not A Palindrome%n",word);}
                    break;
                case "q":
                case "quit":
                    System.out.printf("Ending Process%n");
                    break END;
                default:
                    System.out.printf("%s Is Not A Command%n",cmd);
            }
            scan.nextLine();
        }
    }

    public static boolean checkAnagram(String word1, String word2) {
        char[] word1Array = word1.toCharArray();
        char[] word2Array = word2.toCharArray();
        for (int i=0;i<word1Array.length;i++) {
            char c = word1Array[i];
            char nc = word2Array[word2Array.length-1-i];
            if (c!=nc) {
                return false;
            }
        }
        return true;
    }
    public static boolean checkPalindrome(String word1) {
        char[] wordArray = word1.toCharArray();
        for (int i=0;i<(wordArray.length/2);i++) {
            char c = wordArray[i];
            char nc = wordArray[wordArray.length-1-i];
            if (c!=nc) {
                return false;
            }
        }
        return true;
    }
}
