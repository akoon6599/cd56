package gradeChecker;

import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.function.Function;



    /**
     * "Abnormal Data can't be tested because Java has type checking."
     * <p>
     * And I took that personally. Java type checking is nothing in the face
     * of Generics typing.
     **/


public class Interface {

    // Define a test program that takes a function interface with an argument of any Type and a return type of Character,
    // then goes through the expected bounds of each letter grade and checks them. If a check fails, it throws an
    // AssertionError with a message corresponding to the check that failed. Any non-primitive typed object can be cast
    // to the Generic type T and passed to the Function.apply() method.
    @SuppressWarnings("unchecked cast")
    public static <T> ThrowingRunnable testValue(Function<T,Character> CheckFunction) throws AssertionError {
        try {
            Assert.assertEquals("Grade Check Failed: Extreme F",        (Character) 'F', CheckFunction.apply((T) (Float.valueOf(12.3f))));
            Assert.assertEquals("Grade Check Failed: Value F",          (Character) 'F', CheckFunction.apply((T) (Character.valueOf('F'))));
            Assert.assertEquals("Grade Check Failed: HLimit F",         (Character) 'F', CheckFunction.apply((T) (Integer.valueOf(59))));
            Assert.assertEquals("Grade Check Failed: LLimit D",         (Character) 'D', CheckFunction.apply((T) (Integer.valueOf(60))));
            Assert.assertEquals("Grade Check Failed: Value D",          (Character) 'D', CheckFunction.apply((T) (Character.valueOf('D'))));
            Assert.assertEquals("Grade Check Failed: HLimit D",         (Character) 'D', CheckFunction.apply((T) (Integer.valueOf(69))));
            Assert.assertEquals("Grade Check Failed: LLimit C",         (Character) 'C', CheckFunction.apply((T) (Integer.valueOf(70))));
            Assert.assertEquals("Grade Check Failed: Value C",          (Character) 'C', CheckFunction.apply((T) (Character.valueOf('C'))));
            Assert.assertEquals("Grade Check Failed: HLimit C",         (Character) 'C', CheckFunction.apply((T) (Integer.valueOf(79))));
            Assert.assertEquals("Grade Check Failed: LLimit B",         (Character) 'B', CheckFunction.apply((T) (Integer.valueOf(80))));
            Assert.assertEquals("Grade Check Failed: Value B",          (Character) 'B', CheckFunction.apply((T) (Character.valueOf('B'))));
            Assert.assertEquals("Grade Check Failed: HLimit B",         (Character) 'B', CheckFunction.apply((T) (Integer.valueOf(89))));
            Assert.assertEquals("Grade Check Failed: LLimit A",         (Character) 'A', CheckFunction.apply((T) (Integer.valueOf(90))));
            Assert.assertEquals("Grade Check Failed: Value A",          (Character) 'A', CheckFunction.apply((T) (Character.valueOf('A'))));
            Assert.assertEquals("Grade Check Failed: Extreme A",        (Character) 'A', CheckFunction.apply((T) (Integer.valueOf(200))));
            Assert.assertEquals("Grade Check Failed: Abnormal String",  (Character) 'X', CheckFunction.apply((T) "X"));
        }
        // This will only be thrown if a type unable to be cast to T is used; likely only primitives
        catch (ClassCastException exception) {
            return () -> {
                throw new Throwable("Grade Check Failed: Abnormal Data Type");
            };
        }

        // Return a Throwable exception with a positive test message to cleanly handle successfull tests
        return () -> {
            throw new Throwable("All Checks Passed Successfully");
        };
    }

    @Test
    public void whenExceptionThrown() {
        // Retrieve and store the Throwable exception when running the test program
        // testValue( (x) -> {...}) is set up to run testValue with the function `Character Function(Object x)`
        // where a try/catch block is needed to handle GradeException throws from non-gradable inputs
        Throwable exception = Assert.assertThrows(Throwable.class, testValue((x) -> {
            try {
                return Grading.improvedLetterGrade(x);
            }
            catch (GradeException exception1) {
                throw new RuntimeException(
                        String.format("%s [%s]", exception1.origin, exception1.getMessage())
                );
            }
        }));

        // Check the expected response for a successful test against the received response. If they do not match,
        // pass the received response to the console for error analysis
        String expectedResponse = "All Checks Passed Successfully";
        String receivedResponse = exception.getMessage();
        Assert.assertTrue(receivedResponse, receivedResponse.contains(expectedResponse));
    }
}
