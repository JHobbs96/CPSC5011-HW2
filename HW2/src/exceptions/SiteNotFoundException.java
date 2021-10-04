package exceptions;

public class SiteNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    public SiteNotFoundException(){
        super("Site name does not exist for this user.");
    }

}
