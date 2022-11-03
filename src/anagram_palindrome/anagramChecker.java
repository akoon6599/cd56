package anagram_palindrome;

public class anagramChecker {
    public static void main(String[] args) {
        System.out.println(checkAnagram("locker", "tocker"));
        System.out.println(checkAnagram("doggy", "yggod"));
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
}
