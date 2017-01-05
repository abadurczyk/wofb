package wof.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wof.Application;
import wof.entities.Category;
import wof.services.CategoryService;
import wof.services.WallEntryFacade;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wof.Application.CATEGORY_PATH;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
public class CategoryControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private CategoryService categoryService;
    @MockBean
    private WallEntryFacade wallEntryFacade;

    private MockMvc mockMvc;
    private final String path = CATEGORY_PATH + "/";
    private final String categoryName = "category";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TypeReference<Set<Category>> categoryTypeReference = new TypeReference<Set<Category>>() {
    };

    private final String tooLongCategoryName = StringUtils.repeat("a", 51);
    private final String newCategoryName = "newCategoryName";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void addCategory() throws Exception {
        when(categoryService.addCategory(categoryName)).thenReturn(new Category(categoryName));
        MvcResult mvcResult = mockMvc.perform(post(path + categoryName)
                .contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();

        Category category = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Category.class);
        assertEquals(category.getCategoryName(), categoryName);
    }

    @Test
    public void addCategory_nameNotSupported() throws Exception {
        mockMvc.perform(post(path + tooLongCategoryName)
                .contentType("application/json"))
                .andExpect(status().isPreconditionFailed()).andReturn();

    }

    @Test
    public void getCategories() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(newHashSet(new Category(categoryName)));
        MvcResult mvcResult = mockMvc.perform(get(path)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();

        Set<Category> categorySet = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), categoryTypeReference);
        assertEquals(categorySet.size(), 1);
        assertEquals(categorySet.iterator().next().getCategoryName(), categoryName);
    }

    @Test
    public void changeCategories() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(newHashSet(new Category(categoryName)));
        mockMvc.perform(put(path + categoryName + "/newname/" + newCategoryName)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void changeCategories_oldNameNotSupported() throws Exception {
        mockMvc.perform(put(path + tooLongCategoryName + "/newname/" + newCategoryName)
                .contentType("application/json"))
                .andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void changeCategories_newNameNotSupported() throws Exception {
        mockMvc.perform(put(path + categoryName + "/newname/" + tooLongCategoryName)
                .contentType("application/json"))
                .andExpect(status().isPreconditionFailed()).andReturn();
    }

    @Test
    public void deleteCategory_categoryDeleted() throws Exception {
        mockMvc.perform(delete(path + categoryName)
                .contentType("application/json"))
                .andExpect(status().isNoContent()).andReturn();
    }

    @Test
    public void deleteCategory_categoryTooLong() throws Exception {
        mockMvc.perform(delete(path + tooLongCategoryName)
                .contentType("application/json"))
                .andExpect(status().isNoContent()).andReturn();
        verify(wallEntryFacade, never()).deleteCategory(any());
    }
}
