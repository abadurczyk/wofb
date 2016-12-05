package wof.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wof.services.WallEntryService;

import static org.mockito.Mockito.mock;

@Configuration
public class MockAppConfig {

    @Bean
    public WallEntryService wallEntryService() {
        return mock(WallEntryService.class);
    }
}
