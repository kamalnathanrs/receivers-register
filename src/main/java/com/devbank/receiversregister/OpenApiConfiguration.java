package com.devbank.receiversregister;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfiguration implements WebMvcConfigurer {

	public static final String API_PATH = "api";

	@Primary
	@Bean
	public SwaggerResourcesProvider swaggerResourcesProvider() {
		return () -> {
			List<SwaggerResource> resources = new ArrayList<>();

            resources.add(createSwaggerResource("receivers-register.yaml", "Receivers Register API"));
			return resources;
		};
	}

    private SwaggerResource createSwaggerResource(String yamlFile, String resourceName) {
        SwaggerResource wsResource = new SwaggerResource();
        wsResource.setName(resourceName);
        wsResource.setSwaggerVersion("3.0.0");
        wsResource.setLocation("/" + API_PATH + "/" + yamlFile);
        return wsResource;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        // The URL on which we want to expose the API
        .addResourceHandler("/" + API_PATH + "/*") // Use two ** to include sub folders.
        //The actual place in the jar/war to find a resource relative to src/main/resources.
        .addResourceLocations("classpath:/public/");
    }
}
