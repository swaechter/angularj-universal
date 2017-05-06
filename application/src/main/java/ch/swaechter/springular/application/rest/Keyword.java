package ch.swaechter.springular.application.rest;

/**
 * This class is responsible for representing a keyword with a name.
 *
 * @author Simon Wächter
 */
public class Keyword {

    /**
     * Name of the keyword.
     */
    private final String name;

    /**
     * Create a new keyword based on the name.
     *
     * @param name Name of the keyword
     */
    public Keyword(String name) {
        this.name = name;
    }

    /**
     * Get the name of the keyword.
     *
     * @return Name of the keyword
     */
    public String getName() {
        return name;
    }
}
