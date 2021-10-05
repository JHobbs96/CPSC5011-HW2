package exceptions;

public class DuplicateSiteException extends Exception {

    private static final long serialVersionUID = 1L;
    public DuplicateSiteException(){
        super("The site name already exists for this user.\n");
    }
}
