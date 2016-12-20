package wof.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wof.Application;
import wof.entities.Category;
import wof.services.CategoryService;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wof.Application.CATEGORY_PATH;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
public class CategoryControllerTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private CategoryService categoryService;

    private MockMvc mockMvc;
    private final String path = CATEGORY_PATH + "/";
    private final String categoryName = "category";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TypeReference<Set<Category>> categoryTypeReference = new TypeReference<Set<Category>>() {
    };


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testAdd() throws Exception {
        when(categoryService.addCategory(categoryName)).thenReturn(new Category(categoryName));
        MvcResult mvcResult = mockMvc.perform(post(path + categoryName)
                .contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();

        Category category = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Category.class);
        assertEquals(category.getCategoryName(), categoryName);
    }

    @Test
    public void testGetCategories() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(newHashSet(new Category(categoryName)));
        MvcResult mvcResult = mockMvc.perform(get(path)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();

        Set<Category> categorySet = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), categoryTypeReference);
        assertEquals(categorySet.size(), 1);
        assertEquals(categorySet.iterator().next().getCategoryName(), categoryName);
    }

}
