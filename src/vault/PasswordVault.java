/*
 * Jeremiah Hobbs
 * CPSC 5011, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package vault;
import java.util.HashMap;
import java.util.Map;
import encrypt.CaesarCipher;
import encrypt.Encryptor;

/**
 * A user is the client for the vault. He/she has a username and a vault
 * password, and uses the vault to store (site / password) pairs for a variety
 * of sites. In a real vault a username might be a long string like an email
 * address and a site might be a long URL, but for our example we will use
 * short usernames like "josie" and passwords like "whocares" and short site
 * names like "amazon", "google", or "netflix."
 *
 * @author  Jeremiah Hobbs
 * @version 1.0
 */
public class PasswordVault implements Vault {
    private Map<String, UserData> users;
    private CaesarCipher encryptor;
    private int logInAttempt = 0;


    /**
     * Default Constructor for PasswordVault that initiates the HashMaps.
     */
    public PasswordVault() {
        users = new HashMap<String, UserData>();
//        sites = new HashMap<String, String>();
        encryptor = new CaesarCipher();
    }

    /**TODO
     * Constructor for Password Vault that takes in different Encryptors
     * @param e Encryptor object
     */
    public PasswordVault(Encryptor e) {
        new PasswordVault();
    }

    @Override
    /**
     * Add a new user to the vault (with no site passwords). For example, a
     * username and password is supplied, and the system does no verification
     * or checking except that the username and password must be in correct
     * formats, and the username cannot already be in the vault.
     *
     * @param username The username to be added
     * @param password The password to be associated with this user
     * @throws InvalidUsernameException The supplied username is invalid
     * @throws InvalidPasswordException The supplied password is invalid
     * @throws DuplicateUserException   The username is already in the vault
     */
    public void addNewUser(String username, String password)
            throws exceptions.InvalidUsernameException,
            exceptions.InvalidPasswordException,
            exceptions.DuplicateUserException {
        System.out.println("Attempting to add user "+ username);
        if (username.length() > 11 || username.length() < 6 ) {
            throw new exceptions.InvalidUsernameException();
        }
        if(!username.equals(username.toLowerCase())){
            throw new exceptions.InvalidUsernameException();
        }
        //Make sure the user actually exists
        for (var user : users.keySet()) {
            if (user.equals(username)) {
                throw new exceptions.DuplicateUserException();
            }
        }
        //Make sure password is long enough and valid
        if (password.length() > 6 && password.length() < 15 &&
                password.contains("!") || password.contains("@")
                || password.contains("#") || password.contains("$") ||
                password.contains("%") || password.contains("^") ||
                password.contains("&")) {
            UserData userData = new UserData(username, password);
            users.put(username, userData);
            System.out.println("Added user "+username +"\n");
        } else {
            throw new exceptions.InvalidPasswordException();
        }

    }

    @Override
    /**
     * Adds a new site to the vault for the user, generates, stores, and
     * returns a password for that site. For example, if a user is creating
     * an account at the site "amazon" for the first time, he/she calls the
     * vault with the site name, and the site makes up a password for the
     * user/site, returns the (plain text password to the user, and stores
     * the (encrypted) password -- all stored passwords in the vault.
     *
     * @param username The username requesting the new site password
     * @param password Password for the username
     * @param sitename Name of the site for which the user is requesting a
     *                 password
     * @return A new (plaintext) password for the requested site
     * @throws DuplicateSiteException    There is already a site stored for this
     *                                   user
     * @throws UserNotFoundException     There is no such user in the vault
     * @throws UserLockedOutException    The user has been locked out due to
     *                                   too many incorrect password attempts
     * @throws PasswordMismatchException The password supplied does not match
     *                                   the user's vault password
     * @throws InvalidSiteException      The site name supplied is invalid
     */
    public String addNewSite(String username, String password, String
            sitename)
            throws exceptions.DuplicateSiteException,
            exceptions.UserNotFoundException,
            exceptions.UserLockedOutException,
            exceptions.PasswordMismatchException,
            exceptions.InvalidSiteException {
        System.out.println("Attempting to add website "+ sitename+ " for user "
                + username);
        String pass = "";
        if (!users.containsKey(username)) {
            throw new exceptions.UserNotFoundException();
        }
        // Login to the database.
        if (loginAttempt(username, password)) {
            //Add Scanner stuff here maybe
            //Check to see if the site is already in the vault
            if (users.get(username).data.containsKey(sitename)) {
                    throw new exceptions.DuplicateSiteException();
                }
            }
            if (sitename.length() > 5 && sitename.length() < 13 &&
                sitename.equals(sitename.toLowerCase())){
                //Add the site and encrypt the password
                String randomString = encryptor.randomStringGen();
                String encrypted = encryptor.encrypt(randomString);
                // Return the new password
                System.out.println("Added site "+ sitename+ " for user " +
                        username+ " => generated new password: " +randomString+
                        "\n");
                users.get(username).data.put(sitename,encrypted);
                return randomString;
            } else {
                throw new exceptions.InvalidSiteException();
            }
    }


    @Override
    /**
     * Generate, store, and return an updated password for a site associated
     * with the user. For example, the user wants to change his/her password on
     * "amazon," and the system generates a new password, stores an encrypted
     * version for the user, and returns the plaintext version.
     *
     * @param username The username requesting the new site password
     * @param password Password for the username
     * @param sitename Name of the site for which the user is requesting a
     *                 password
     * @return An updated (plaintext) password for the requested site
     * @throws SiteNotFoundException     The user has no password associated
     *                                   with this site
     * @throws UserNotFoundException     There is no such user in the vault
     * @throws UserLockedOutException    The user has been locked out due to too
     *                                   many incorrect password attempts
     * @throws PasswordMismatchException The password supplied does not match
     *                                  the user's vault password
     */
    public String updateSitePassword(String username, String
            password, String sitename)
            throws exceptions.SiteNotFoundException,
            exceptions.UserNotFoundException,
            exceptions.UserLockedOutException,
            exceptions.PasswordMismatchException {
        System.out.println("Attempting to update website password " + sitename +
                " for user " +
                username);
        String pass = "";
        if (!users.containsKey(username)) {
            throw new exceptions.UserNotFoundException();
        }
        // Login to the database.
        if (loginAttempt(username, password)) {
            //Add Scanner stuff here maybe
            //Check to see if the site is already in the vault
            if (users.get(username).data.containsKey(sitename)) {
                //Remove the key, change password, and tell the user.
                users.get(username).data.remove(sitename);
                String newPass = encryptor.randomStringGen();
                String crypt = encryptor.encrypt(newPass);
                System.out.println("Updated site " + sitename + " for user " +
                        username + " => updated password: " + newPass + "\n");
                users.get(username).data.put(sitename, crypt);
                return newPass;
            } else {
                throw new exceptions.SiteNotFoundException();
            }
        }
        return pass;
    }

    @Override
    /**
         * Retrieve the (plaintext) password for the user for the requested site.
         * For example, the user supplies the name of a site, and if she has a
         * stored password for the site, it is returned in plain text.
         *
         * @param username The username requesting the site password
         * @param password Password for the username
         * @param sitename Name of the site for which the user is requesting a
         *                      password
         * @return The (plaintext) password for the requested site
         * @throws SiteNotFoundException     The user has no password
         *                                   associated with this site
         * @throws UserNotFoundException     There is no such user in the vault
         * @throws UserLockedOutException    The user has been locked out due
         *                                   to too many incorrect password
         *                                       attempts
         * @throws PasswordMismatchException The password supplied does not
         *                                      match the user's vault password
         */
        public String retrieveSitePassword (String username, String
        password, String sitename)
            throws exceptions.SiteNotFoundException,
            exceptions.UserNotFoundException,
            exceptions.UserLockedOutException,
            exceptions.PasswordMismatchException {
        System.out.println("Attempting to retrieve website password "+ sitename+
                " for user " +
                username);
        String pass = "";
        if (!users.containsKey(username)) {
            throw new exceptions.UserNotFoundException();
        }
        // Login to the database.
        if (loginAttempt(username, password)) {
            //Add Scanner stuff here maybe
            //Check to see if the site is already in the vault
                if (users.get(username).data.containsKey(sitename)) {
                    //Retrieve the password and decrypt it and Tell the user.
                    String crypt = users.get(username).data.get(sitename);
                    pass = encryptor.decrypt(crypt);
                    System.out.println("Retrieved site "+ sitename+ " for user "
                            + username+ " => retrieved password: " + pass +
                            "\n");
                    return pass;
                } else {
                    throw new exceptions.SiteNotFoundException();
                }
            }
        return pass;
        }

    /**
     * Private method that returns a boolean that informs successful login.
     * @param username user attempting to log in
     * @param password password to users map/vault
     * @return true if successful login
     * @throws exceptions.PasswordMismatchException The password supplied
     *                                  does not match the user's vault password
     * @throws exceptions.UserLockedOutException The user has been locked out
     *                                  due too many incorrect password
     *                                  attempts
     */
    private boolean loginAttempt(String username, String password) throws
            exceptions.PasswordMismatchException,
            exceptions.UserLockedOutException {
        if (logInAttempt < 3) {
            if (users.get(username).password.equals(password)) {
                logInAttempt = 0;
                return true;
            } else {
                logInAttempt++;
                throw new exceptions.PasswordMismatchException();
            }
        } else {
            throw new exceptions.UserLockedOutException();
        }
    }



}
