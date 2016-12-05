package wof.controllers;


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
import wof.entities.WallEntry;
import wof.services.WallEntryService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
public class MainControllerTest {


    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private WallEntryService wallEntryService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void addEntry() throws Exception {
        String headline = "headline";
        String description = "description";
        WallEntry expectedWallEntry = new WallEntry();
        expectedWallEntry.setDescription(description);
        expectedWallEntry.setHeadline(headline);
        String uri = String.format("/v1/headline/%s/description/%s", headline, description);
        when(wallEntryService.add(headline, description)).thenReturn(expectedWallEntry);

        MvcResult mvcResult = mockMvc.perform(post(uri)
                .contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();
        WallEntry actualWallEntry = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), WallEntry.class);
        assertEquals(expectedWallEntry, actualWallEntry);

        verify(wallEntryService).add(headline, description);
    }

}
