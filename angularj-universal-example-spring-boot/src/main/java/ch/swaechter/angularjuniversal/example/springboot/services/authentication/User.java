package ch.swaechter.angularjuniversal.example.springboot.services.authentication;

/**
 * This class represents the login entity that is used for authentication.
 *
 * @author Simon Wächter
 */
public class User {

    /**
     * Name of the user.
     */
    private String username;

    /**
     * Password of the user
     */
    private String password;

    /**
     * Get the name of the user.
     *
     * @return Name of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the name of the user.
     *
     * @param username New name of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password of the user.
     *
     * @return Password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the user.
     *
     * @param password New password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
