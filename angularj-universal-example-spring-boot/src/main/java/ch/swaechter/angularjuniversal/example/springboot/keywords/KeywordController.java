package ch.swaechter.angularjuniversal.example.springboot.keywords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for providing all keywords.
 *
 * @author Simon Wächter
 */
@RestController
@RequestMapping("/api")
public class KeywordController {

    private final KeywordService keywordservice;

    /**
     * Constructor with the keyword service.
     *
     * @param keywordservice Keyword service
     */
    @Autowired
    public KeywordController(KeywordService keywordservice) {
        this.keywordservice = keywordservice;
    }

    /**
     * Get all keywords.
     *
     * @return All keywords
     */
    @GetMapping("/keyword")
    public ResponseEntity<List<Keyword>> getKeywords() {
        return new ResponseEntity<>(keywordservice.getKeywords(), HttpStatus.OK);
    }

    @GetMapping("/keyword/{id}")
    public ResponseEntity<Keyword> getKeyword(@PathVariable("id") Integer id) {
        Optional<Keyword> keyword = keywordservice.getKeyword(id);
        return keyword.map(keyword1 -> new ResponseEntity<>(keyword1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
