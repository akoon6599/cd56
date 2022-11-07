package rot13;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class rot13 {
    static Integer Rotation;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter String to Encode");
        String toEncode = scanner.nextLine();
        System.out.println("Enter Rotation. Press Enter for Default");
        String tmp = scanner.nextLine();
        Rotation = (!tmp.isEmpty() && tmp.matches("[0-9]*"))?Integer.getInteger(tmp):13;
        // Check if line is not empty and is integer, then set. if not use default

        Matcher matcher = Pattern.compile(" ?([a-zA-Z]+)(\\p{Punct}?+)").matcher(toEncode); // create regex that matches each word
        ArrayList<String> Sections = new ArrayList<>();
        ArrayList<String> Punctuation = new ArrayList<>();
        // go through each match and add to arrays
        while (matcher.find()) {
            Sections.add(matcher.group(1));
            Punctuation.add(!matcher.group(2).isBlank()?matcher.group(2):""); // if punctuation group is not blank, add. else, add blank
        }
        StringBuilder fnl = new StringBuilder();
        for (String sect : Sections) {
            fnl.append(String.format("%s%s ", encode(sect),Punctuation.get(0))); // format into word>punctuation>space
            Punctuation.remove(0); // remove foremost punctuation to continue iterating through
        }
        System.out.println(fnl);
    }

    private static String encode(String toEncode) {
        StringBuilder rtn = new StringBuilder();
        for (char ch : toEncode.toCharArray()) {
            int relativeZeroCap = 'A'; // define lower bounds of the cases
            int relativeZeroLow = 'a';
            int newChar = (int)ch + Rotation;

            // Check if newChar is above capitals but original is below lowers, then if outside alphabet scope
            if ((newChar > relativeZeroCap && (int)ch < relativeZeroLow) && (newChar-relativeZeroCap) > 26) {
                newChar = (newChar - relativeZeroCap) % 26 + relativeZeroCap; // wrap around then return to lower bound
            }
            // Similar as above, except for lowercase
            if ((newChar > relativeZeroLow) && (newChar-relativeZeroLow) > 26) {
                newChar = (newChar - relativeZeroLow) % 26 + relativeZeroLow;
            }

            rtn.append((char)newChar);
        }
        return rtn.toString();
    }
}
