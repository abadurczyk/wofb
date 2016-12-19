package wof.integrationtests;

import cucumber.api.java8.En;

import static org.junit.Assert.assertEquals;

public class WallTests implements En {
    public WallTests() {

        Then("^The test is not failing$", () -> {
            assertEquals(1, 1);

        });
    }

}
