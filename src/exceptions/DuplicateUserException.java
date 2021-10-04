package exceptions;

public class DuplicateUserException extends Exception {

    private static final long serialVersionUID = 1L;
    public DuplicateUserException(){
        super("The username already exists.\n");
    }
}
