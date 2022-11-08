package com.ninjaone.backendinterviewproject.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
public class SwaggerConfiguration {

	@Bean
    public OpenAPI swaggerDocOpenApi() {
        return new OpenAPI()
        				.info(new Info().title("RMM - Remote Monitoring and Management")
                        .description("A Remote Monitoring and Management (RMM) platform helps IT professionals manage a "
                        		+ "fleet of Devices with Services associated with them.")
                        .version("v0.0.1"));
    }
}
