package es.heroesfactory.config;

import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI heroesOpenAPI() {

        final String securitySchemeName = "http";
        final String apiTitle = "Heroes API";

        return new OpenAPI().info(new Info().title(apiTitle)
                        .description("Heroes complete API")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .version("1.0"));
    }
}
