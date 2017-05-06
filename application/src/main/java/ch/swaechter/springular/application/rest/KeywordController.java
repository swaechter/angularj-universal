package ch.swaechter.springular.application.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for providing all keywords.
 *
 * @author Simon Wächter
 */
@RestController
@RequestMapping("/api")
public class KeywordController {

    /**
     * Get all keywords.
     *
     * @return All keywords
     */
    @GetMapping("/keyword")
    public ResponseEntity<List<Keyword>> getKeywords() {
        List<Keyword> keywords = Arrays.asList(new Keyword("Angular"), new Keyword("TypeScript"), new Keyword("J2V8"));
        return new ResponseEntity<>(keywords, HttpStatus.OK);
    }
}
