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
        while (true) {
            System.out.print(">>> ");

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
        int last_post = wordArray.length-1;
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
