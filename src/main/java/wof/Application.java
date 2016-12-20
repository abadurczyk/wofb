package wof;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class Application {

    public static final String WALL_PATH = "/v1/wall";
    public static final String CATEGORY_PATH = "/v1/category";


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // NOSONAR
    }

   /* @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }*/
}
