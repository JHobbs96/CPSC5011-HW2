package vault;

import java.util.HashMap;
import java.util.Map;

import encrypt.CaesarCipher;
import encrypt.Encryptor;

public class PasswordVault implements Vault {
    private Map<String, String> users;
    private Map<String, String> sites;
    private CaesarCipher encryptor;
    private int logInAttempt = 0;



    public PasswordVault() {
        users = new HashMap<String, String>();
        sites = new HashMap<String, String>();
        encryptor = new CaesarCipher();
    }

    public PasswordVault(Encryptor e) {
        users = new HashMap<String, String>();
        sites = new HashMap<String, String>();
        encryptor = new CaesarCipher();
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
            users.put(username, password);
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
     * @param sitename Name of the site for which the user is requesting a password
     * @return A new (plaintext) password for the requested site
     * @throws DuplicateSiteException    There is already a site stored for this
     *                                   user
     * @throws UserNotFoundException     There is no such user in the vault
     * @throws UserLockedOutException    The user has been locked out due to too
     *                                   many incorrect password attempts
     * @throws PasswordMismatchException The password supplied does not match the
     *                                   user's vault password
     * @throws InvalidSiteException      The site name supplied is invalid
     */
    public String addNewSite(String username, String password, String
            sitename)
            throws exceptions.DuplicateSiteException,
            exceptions.UserNotFoundException,
            exceptions.UserLockedOutException,
            exceptions.PasswordMismatchException,
            exceptions.InvalidSiteException {
        String pass = "";
        if (!users.containsKey(username)) {
            throw new exceptions.UserNotFoundException();
        }
        // Login to the database.
        if (loginAttempt(username, password)) {
            //Add Scanner stuff here maybe
            //Check to see if the site is already in the vault
            for (var mySite : sites.keySet()) {
                if (mySite.equals(sitename)) {
                    throw new exceptions.DuplicateSiteException();
                }
            }
            if (sitename.length() > 5 && sitename.length() < 13 &&
                sitename.equals(sitename.toLowerCase())){
                //Add the site and encrypt the password
                CaesarCipher cipher = new CaesarCipher();
                String randomString = cipher.randomStringGen();
//                System.out.println(randomString);
                String encrypted = cipher.encrypt(randomString);
//                System.out.println(encrypted);
                // Return the new password
                System.out.println("Attempting to add site "+sitename +
                        " for user " +username);
                System.out.println("Added site "+ sitename+ " for user " +
                        username+ " => generated new password: " +randomString+
                        "\n");
                sites.put(sitename,encrypted);
                return randomString;
            } else {
                throw new exceptions.InvalidSiteException();
            }
        }
        return pass;

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
     * @param sitename Name of the site for which the user is requesting a password
     * @return An updated (plaintext) password for the requested site
     * @throws SiteNotFoundException     The user has no password associated with
     *                                   this site
     * @throws UserNotFoundException     There is no such user in the vault
     * @throws UserLockedOutException    The user has been locked out due to too
     *                                   many incorrect password attempts
     * @throws PasswordMismatchException The password supplied does not match the
     *                                   user's vault password
     */
    public String updateSitePassword(String username, String
            password, String sitename)
            throws exceptions.SiteNotFoundException,
            exceptions.UserNotFoundException,
            exceptions.UserLockedOutException,
            exceptions.PasswordMismatchException {
        String pass = "";
        if (!users.containsKey(username)) {
            throw new exceptions.UserNotFoundException();
        }
        // Login to the database.
        if (loginAttempt(username, password)) {
            //Add Scanner stuff here maybe
            //Check to see if the site is already in the vault
            for (var mySite : sites.keySet()) {
                if (mySite.equals(sitename)) {
                    sites.remove(mySite);
                    String newPass = encryptor.randomStringGen();
                    String crypt = encryptor.encrypt(newPass);

                    System.out.println("Updated site "+ sitename+ " for user " +
                            username+ " => updated password: " + newPass +"\n");
                    sites.put(mySite, crypt);
                    return newPass;
                } else {
                    throw new exceptions.SiteNotFoundException();
                }
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
         * @param sitename Name of the site for which the user is requesting a password
         * @return The (plaintext) password for the requested site
         * @throws SiteNotFoundException     The user has no password associated with
         *                                   this site
         * @throws UserNotFoundException     There is no such user in the vault
         * @throws UserLockedOutException    The user has been locked out due to too
         *                                   many incorrect password attempts
         * @throws PasswordMismatchException The password supplied does not match the
         *                                   user's vault password
         */
        public String retrieveSitePassword (String username, String
        password, String sitename)
            throws exceptions.SiteNotFoundException,
            exceptions.UserNotFoundException,
            exceptions.UserLockedOutException,
            exceptions.PasswordMismatchException {
        String pass = "";
        if (!users.containsKey(username)) {
            throw new exceptions.UserNotFoundException();
        }
        // Login to the database.
        if (loginAttempt(username, password)) {
            //Add Scanner stuff here maybe
            //Check to see if the site is already in the vault
            for (var mySite : sites.keySet()) {
                if (mySite.equals(sitename)) {
                    String crypt = sites.get(sitename);
                    pass = encryptor.decrypt(crypt);
                    System.out.println("Retrieved site "+ sitename+ " for user "
                            + username+ " => retrieved password: " + pass + "\n");
                    return pass;
                } else {
                    throw new exceptions.SiteNotFoundException();
                }
            }

        }
        return pass;

    }

    private boolean loginAttempt(String username, String password) throws
            exceptions.PasswordMismatchException,
            exceptions.UserLockedOutException {
        if (logInAttempt < 3) {
            if (users.get(username).equals(password)) {
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
