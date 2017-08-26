package ch.swaechter.angularjuniversal.example.springboot.services.keyword;

import java.util.List;

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
}
