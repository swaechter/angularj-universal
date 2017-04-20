package ch.swaechter.springular.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringularApplication.class, SpringularApplicationTest.TestConfig.class})
public class SpringularApplicationTest {

    @Test
    public void integrationTest() {
    }

    @TestConfiguration
    public static class TestConfig {
    }
}
