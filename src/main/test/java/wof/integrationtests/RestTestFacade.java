package wof.integrationtests;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import wof.entities.Category;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static wof.Application.CATEGORY_PATH;


public class RestTestFacade {
    private static RestTemplate restTemplate = new RestTemplate();
    private static String categoryPath = CATEGORY_PATH;
    private static final String REST_PATH = "http://localhost:8080";
    private static HttpHeaders headers = new HttpHeaders();


    private static Set<Category> categorySet = newHashSet();

    @Before
    public void setup() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @After
    public void cleanUp() {
        for (Category c : categorySet) {
            deleteCategory(c);
        }
        categorySet.clear();
    }

    private static void deleteCategory(Category c) {
        restTemplate.exchange(REST_PATH + categoryPath + "/" + c.getCategoryName(), HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
    }

    public static void createCategory(String categoryName) {
        Category c = restTemplate.exchange(REST_PATH + categoryPath + "/" + categoryName, HttpMethod.POST, new HttpEntity<>(headers),
                Category.class).getBody();
        categorySet.add(c);
    }

    public static void categoryExists(String categoryName) {
        restTemplate.exchange(REST_PATH + categoryPath + "/" + categoryName, HttpMethod.GET, new HttpEntity<>(headers), Category.class);
    }
}
