/*
 * Jeremiah Hobbs
 * CPSC 5011, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package vault;
import java.util.HashMap;

/**
 * The UserData class stores valuable information about the user such as
 * username, password, and all of the passwords encrypted.
 */
public class UserData {
    public String username;
    public String password;
    public HashMap<String, String> data;


    public UserData(String username,String password){
        this.username = username;
        this.password = password;
        data = new HashMap<String, String>();
    }
}
