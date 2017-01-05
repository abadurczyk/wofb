package wof.integrationtests;

import cucumber.api.java8.En;

public class CategoryTestSteps implements En {
    public CategoryTestSteps() {
        When("^A category named \"([^\"]*)\" is added$", (String categoryName) -> {
            RestTestFacade.createCategory(categoryName);
        });
        Then("^A category named \"([^\"]*)\" exists$", (String categoryName) -> {
            RestTestFacade.categoryExists(categoryName);
        });
    }
}
