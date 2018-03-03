package ch.swaechter.angularjuniversal.example.springboot.keywords;

import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for defining the keyword service.
 *
 * @author Simon Wächter
 */
public interface KeywordService {

    /**
     * Get all keywords.
     *
     * @return All keywords as list
     */
    List<Keyword> getKeywords();

    /**
     * get a specific keyword.
     *
     * @param id ID of the keyword
     * @return Optional keyword
     */
    Optional<Keyword> getKeyword(int id);
}
