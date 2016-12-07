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
import wof.entities.WallEntry;
import wof.exceptions.HeadlineCannotBeEmptyException;
import wof.services.WallEntryService;

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

@Controller
@RequestMapping("/v1")
@CrossOrigin(maxAge = 3600,
        methods = {OPTIONS, GET, PUT, POST, DELETE},
        allowedHeaders = {ORIGIN, CONTENT_TYPE, ACCEPT, AUTHORIZATION})
public class WallController {

    @Autowired
    private WallEntryService wallEntryService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody()
    @ResponseStatus(HttpStatus.CREATED)
    public WallEntry addEntryWithBody(@RequestBody WallEntry wallEntry) throws Exception {
        checkForHeadline(wallEntry);
        return wallEntryService.add(wallEntry.getHeadline(), wallEntry.getDescription());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Set<WallEntry> getAll() {
        return wallEntryService.getAllEntries();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") int id, WallEntry wallEntry) throws Exception {
        checkForHeadline(wallEntry);
        wallEntryService.update(id, wallEntry);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        wallEntryService.delete(id);
    }

    private void checkForHeadline(WallEntry wallEntry) throws Exception {
        if ("".equalsIgnoreCase(wallEntry.getHeadline())) {
            throw new HeadlineCannotBeEmptyException();
        }
    }

}
