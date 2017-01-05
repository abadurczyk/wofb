package wof.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import wof.entities.Category;
import wof.entities.WallEntry;
import wof.exceptions.CategoryNotFoundException;
import wof.services.CategoryService;
import wof.services.WallEntryService;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.ORIGIN;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static wof.Application.WALL_PATH;
import static wof.utils.WallControllerUtils.checkHeadline;

@Controller
@RequestMapping(value = WALL_PATH, consumes = "application/json", produces = "application/json")
@CrossOrigin(maxAge = 3600,
        methods = {OPTIONS, GET, PUT, POST, DELETE},
        allowedHeaders = {ORIGIN, CONTENT_TYPE, ACCEPT, AUTHORIZATION})
public class WallController {

    private final WallEntryService wallEntryService;
    private final CategoryService categoryService;

    @Autowired
    public WallController(WallEntryService wallEntryService, CategoryService categoryService) {
        this.wallEntryService = wallEntryService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody()
    @ResponseStatus(HttpStatus.CREATED)
    public WallEntry addEntryWithBody(@RequestBody WallEntry wallEntry) {
        checkHeadline(wallEntry.getHeadline());
        if (wallEntry.getCategories() == null) {
            throw new CategoryNotFoundException();
        }
        Set<Category> categories = getCategoriesForWallEntries(wallEntry);
        wallEntry.setCategories(categories);
        return wallEntryService.add(wallEntry);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Set<WallEntry> getAll() {
        return wallEntryService.getAllEntries();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") int id, WallEntry wallEntry) {
        checkHeadline(wallEntry.getHeadline());
        wallEntryService.update(id, wallEntry);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        wallEntryService.delete(id);
    }


    private Set<Category> getCategoriesForWallEntries(@RequestBody WallEntry wallEntry) {
        Set<String> categoryNames = newHashSet();
        for (Category c : wallEntry.getCategories()) {
            categoryNames.add(c.getCategoryName());
        }
        return categoryService.getCategories(categoryNames);
    }
}
