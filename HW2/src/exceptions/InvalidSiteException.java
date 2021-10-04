package exceptions;

public class InvalidSiteException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidSiteException(){
        super("The site name is invalid; enter 6-12 lower-case letters.\n");
    }
}
