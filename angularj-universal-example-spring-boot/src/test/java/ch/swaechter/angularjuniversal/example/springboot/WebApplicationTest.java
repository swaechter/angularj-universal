package ch.swaechter.angularjuniversal.example.springboot;

import ch.swaechter.angularjuniversal.keywords.Keyword;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class provides an environment for testing the real web application.
 *
 * @author Simon Wächter
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebApplicationTest {

    /**
     * Use a rest template to handle HTTP requests.
     */
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Test the API.
     */
    @Test
    public void testApi() {
        ResponseEntity<Keyword[]> responseEntity = restTemplate.getForEntity("/api/keyword", Keyword[].class);
        Keyword[] keywords = responseEntity.getBody();
        Assert.assertEquals(3, keywords.length);
        Assert.assertEquals("Hallo Welt!", keywords[0].getName());
        Assert.assertEquals("Hello world!", keywords[1].getName());
        Assert.assertEquals("Здравствуй, мир!", keywords[2].getName());
    }

    /**
     * Test all Angular pages.
     */
    @Test
    public void testPages() {
        String page1 = restTemplate.getForObject("/", String.class);
        Assert.assertTrue(page1.contains("Home"));

        String page2 = restTemplate.getForObject("/home", String.class);
        Assert.assertTrue(page2.contains("Home"));

        String page3 = restTemplate.getForObject("/keywords", String.class);
        Assert.assertTrue(page3.contains("Keywords"));

        String page4 = restTemplate.getForObject("/keywords/1", String.class);
        // Assert.assertTrue(page4.contains("Dummy keyword")); // TODO: Fix routing in Angular

        String page5 = restTemplate.getForObject("/about", String.class);
        Assert.assertTrue(page5.contains("About"));
    }
}
