package exceptions;

public class UserLockedOutException extends Exception {
    private static final long serialVersionUID = 1L;
    public UserLockedOutException(){
        super("Attempted to login with incorrect credentials 3 times. " +
                "User is locked out of the system.");
    }
}
