package ch.swaechter.angularjuniversal.example.springboot.keywords;

import ch.swaechter.angularjuniversal.keywords.Keyword;
import ch.swaechter.angularjuniversal.keywords.KeywordService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class is responsible for providing all keywords.
 *
 * @author Simon Wächter
 */
@RestController
@RequestMapping("/api")
public class KeywordController {

    @NotNull
    private final KeywordService keywordService;

    /**
     * Default constructor.
     */
    public KeywordController() {
        this.keywordService = new KeywordService();
    }

    /**
     * Get all keywords.
     *
     * @return All keywords
     */
    @GetMapping("/keyword")
    @NotNull
    public ResponseEntity<List<Keyword>> getKeywords() {
        return new ResponseEntity<>(keywordService.getKeywords(), HttpStatus.OK);
    }
}
