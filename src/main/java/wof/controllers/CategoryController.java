package wof.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import wof.entities.Category;
import wof.services.CategoryService;
import wof.services.WallEntryFacade;

import java.util.Set;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.ORIGIN;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static wof.Application.CATEGORY_PATH;

@Controller
@RequestMapping(value = CATEGORY_PATH, consumes = "application/json", produces = "application/json")
@CrossOrigin(maxAge = 3600,
        methods = {OPTIONS, GET, PUT, POST, DELETE},
        allowedHeaders = {ORIGIN, CONTENT_TYPE, ACCEPT, AUTHORIZATION})
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;
    private final WallEntryFacade wallEntryFacade;

    @Autowired
    public CategoryController(CategoryService categoryService, WallEntryFacade wallEntryFacade) {
        this.categoryService = categoryService;
        this.wallEntryFacade = wallEntryFacade;
    }


    @RequestMapping(value = "/{category}", method = RequestMethod.POST)
    @ResponseBody()
    @ResponseStatus(HttpStatus.CREATED)
    public Category addCategory(@PathVariable("category") String category) {
        return categoryService.addCategory(category);
    }

    @RequestMapping(value = "/{category}/newname/{newCategoryName}", method = RequestMethod.GET)
    @ResponseBody()
    public Set<Category> getCategories(@PathVariable("category") String oldCategoryName, @PathVariable("newCategoryName") String newCategoryName) {
        return categoryService.getAllCategories();
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseBody()
    public Set<Category> changeCategory() {
        return categoryService.getAllCategories();
    }

    @RequestMapping(value = "/{category}", method = RequestMethod.DELETE)
    @ResponseBody()
    public void deleteCategory(@PathVariable("category") String category) {
        log.info("deleting category=" + category);
        wallEntryFacade.deleteCategory(category);
    }
}
