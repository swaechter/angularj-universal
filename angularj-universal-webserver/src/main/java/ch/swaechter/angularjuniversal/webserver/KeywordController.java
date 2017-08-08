package ch.swaechter.angularjuniversal.webserver;

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
        Keyword keyword1 = new Keyword();
        keyword1.setName("Angular");

        Keyword keyword2 = new Keyword();
        keyword2.setName("TypeScript");

        Keyword keyword3 = new Keyword();
        keyword3.setName("J2V8");

        List<Keyword> keywords = Arrays.asList(keyword1, keyword2, keyword3);
        return new ResponseEntity<>(keywords, HttpStatus.OK);
    }
}
