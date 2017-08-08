package ch.swaechter.angularjuniversal.webserver;

/**
 * This class is responsible for representing a keyword with a name.
 *
 * @author Simon Wächter
 */
public class Keyword {

    /**
     * Name of the keyword.
     */
    private String name;

    /**
     * Default constructor.
     */
    public Keyword() {
    }

    /**
     * Get the name of the keyword.
     *
     * @return Name of the keyword
     */
    public String getName() {
        return name;
    }

    /**
     * Set the new name of the keyword.
     *
     * @param name New name of the keyword
     */
    public void setName(String name) {
        this.name = name;
    }
}
