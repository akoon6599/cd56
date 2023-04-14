package gradeChecker;

// non-conventional `origin` is used to denote the reason for GradeException being thrown, while `message` is used
// to denote the original object/value/type that caused the exception in string form
public class GradeException extends Exception {
    public String origin;
    public GradeException(String origin, String message) {
        super(message);
        this.origin = origin;
    }
}
