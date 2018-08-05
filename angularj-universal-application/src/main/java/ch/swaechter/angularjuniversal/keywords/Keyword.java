package ch.swaechter.angularjuniversal.keywords;

/**
 * This class is responsible for representing a keyword with a name.
 *
 * @author Simon Wächter
 */
public class Keyword {

    /**
     * ID of the keyword.
     */
    private Integer id;

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
     * Constructor with the ID and the name of the keyword.
     *
     * @param id   ID of the keyword
     * @param name Name of the keyword
     */
    public Keyword(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Get the ID of the keyword.
     *
     * @return ID of the keyword
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the new ID of the keyword.
     *
     * @param id New ID of the keyword
     */

    public void setId(Integer id) {
        this.id = id;
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
