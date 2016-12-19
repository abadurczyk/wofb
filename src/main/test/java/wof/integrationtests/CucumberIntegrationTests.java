package wof.integrationtests;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import wof.Application;

@RunWith(Cucumber.class)
@SpringBootTest(classes = Application.class)
// setting strict option so cucumber fails when a step definition is missing
@CucumberOptions(strict = true, format = {"pretty", "html:target/cucumber"})
@ContextConfiguration(classes = Application.class, loader = AnnotationConfigContextLoader.class)
@WebAppConfiguration
public class CucumberIntegrationTests {
}
