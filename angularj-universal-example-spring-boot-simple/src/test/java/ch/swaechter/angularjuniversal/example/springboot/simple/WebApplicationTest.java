package ch.swaechter.angularjuniversal.example.springboot.simple;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
    private TestRestTemplate resttemplate;

    /**
     * Test all Angular pages.
     */
    @Test
    public void testPages() {
        String page1 = resttemplate.getForObject("/", String.class);
        Assert.assertTrue(page1.contains("Login"));

        String page2 = resttemplate.getForObject("/login", String.class);
        Assert.assertTrue(page2.contains("Login"));

        String page3 = resttemplate.getForObject("/logout", String.class);
        Assert.assertTrue(page3.contains("Login"));

        String page4 = resttemplate.getForObject("/page", String.class);
        Assert.assertTrue(page4.contains("Home"));

        String page5 = resttemplate.getForObject("/page/home", String.class);
        Assert.assertTrue(page5.contains("Home"));

        String page6 = resttemplate.getForObject("/page/about", String.class);
        Assert.assertTrue(page6.contains("About"));
    }
}
