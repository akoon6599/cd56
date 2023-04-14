package gradeChecker;


import java.util.HashMap;

public class Grading {

    @Deprecated
    public static Character giveLetterGrade(Integer numGrade) {
        if (numGrade < 60) {
            return 'F';
        }
        else if (numGrade < 70) {
            return 'D';
        }
        else if (numGrade < 80) {
            return 'C';
        }
        else if (numGrade < 90) {
            return 'B';
        }
        else if (numGrade < 100) {
            return 'A';
        }
        else return '?';
    }

    public static <T> Character improvedLetterGrade(T origGrade) throws GradeException {
        // Define map for easy changing of upper limit of grade bounds
        HashMap<Character, Integer> UpperGradeBoundaries = new HashMap<>();
        UpperGradeBoundaries.put('-', -1);
        UpperGradeBoundaries.put('X',  0);
        UpperGradeBoundaries.put('F', 60);
        UpperGradeBoundaries.put('D', 70);
        UpperGradeBoundaries.put('C', 80);
        UpperGradeBoundaries.put('B', 90);
        UpperGradeBoundaries.put('A', 200);

        // Handle origGrade based on type, handled via implicit casting from T
        switch (origGrade.getClass().getSimpleName()) {
            case "String" -> {
                // If the provided grade is a single letter that corresponds to a grade, return that letter grade
                char[] charArray = origGrade.toString().toCharArray();
                if (charArray.length == 1 && UpperGradeBoundaries.containsKey(charArray[0])) {
                    return charArray[0];
                }
                // If not, declare a malformed data entry
                else {
                    throw new GradeException("Grade Check Failed: Abnormal String Value", origGrade.toString());
                }
            }
            case "Character" -> {
                if (UpperGradeBoundaries.containsKey((Character)origGrade)) {return (Character)origGrade;}
                else {
                    throw new GradeException("Grade Check Failed: Abnormal Character Value", origGrade.toString());
                }
            }
            case "Integer" -> {
                Integer objGrade = Integer.valueOf(origGrade.toString());
                // If a grade is lower than the value for X, declare a malformed data entry. Otherwise, return the correct letter grade
                if (objGrade <= UpperGradeBoundaries.get('-')) {throw new GradeException("Grade Check Failed: Lower Bound Exception", origGrade.toString());}
                else if (objGrade.equals(UpperGradeBoundaries.get('X'))) {return 'X';}
                else if (objGrade < UpperGradeBoundaries.get('F')) {return 'F';}
                else if (objGrade < UpperGradeBoundaries.get('D')) {return 'D';}
                else if (objGrade < UpperGradeBoundaries.get('C')) {return 'C';}
                else if (objGrade < UpperGradeBoundaries.get('B')) {return 'B';}
                else if (objGrade <= UpperGradeBoundaries.get('A')) {return 'A';}
                // If a grade is higher than the value fo A, declare a malformed data entry
                else {
                    throw new GradeException("Grade Check Failed: Upper Bound Exception", origGrade.toString());
                }
            }
            // In the case of a float grade, round to an Integer and re-perform the check
            case "Float" -> {
                return improvedLetterGrade(Math.round(Float.parseFloat(origGrade.toString())));
            }
            // In the case of any other type, declare a malformed data entry
            default -> throw new GradeException("Grade Check Failed: Abnormal Data Type", origGrade.getClass().toString());
        }
    }
}
