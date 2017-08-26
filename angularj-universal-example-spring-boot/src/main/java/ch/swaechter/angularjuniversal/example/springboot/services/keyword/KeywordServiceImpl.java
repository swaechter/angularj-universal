package ch.swaechter.angularjuniversal.example.springboot.services.keyword;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for providing all keywords.
 *
 * @author Simon Wächter
 */
@Service
public class KeywordServiceImpl implements KeywordService {

    /**
     * Get all keywords.
     *
     * @return All keywords as list
     */
    @Override
    public List<Keyword> getKeywords() {
        Keyword keyword1 = new Keyword();
        keyword1.setName("Angular");

        Keyword keyword2 = new Keyword();
        keyword2.setName("TypeScript");

        Keyword keyword3 = new Keyword();
        keyword3.setName("J2V8");

        return Arrays.asList(keyword1, keyword2, keyword3);
    }
}
