package ch.swaechter.angularjuniversal.keywords;

import org.jetbrains.annotations.NotNull;

/**
 * This class is responsible for representing a keyword with a name.
 *
 * @author Simon Wächter
 */
public class Keyword {

    /**
     * ID of the keyword.
     */
    @NotNull
    private Integer id;

    /**
     * Name of the keyword.
     */
    @NotNull
    private String name;

    /**
     * Default constructor.
     */
    public Keyword() {
        this.id = (int) Math.round(Math.random() * Integer.MAX_VALUE);
        this.name = "";
    }

    /**
     * Constructor with the ID and the name of the keyword.
     *
     * @param id   ID of the keyword
     * @param name Name of the keyword
     */
    public Keyword(@NotNull Integer id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Get the ID of the keyword.
     *
     * @return ID of the keyword
     */
    @NotNull
    public Integer getId() {
        return id;
    }

    /**
     * Set the new ID of the keyword.
     *
     * @param id New ID of the keyword
     */
    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    /**
     * Get the name of the keyword.
     *
     * @return Name of the keyword
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Set the new name of the keyword.
     *
     * @param name New name of the keyword
     */
    public void setName(@NotNull String name) {
        this.name = name;
    }
}
