package com.ellixo.healthcare.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;

@Configuration
@EnableSwagger
public class SwaggerConfig implements EnvironmentAware {

    public static final String DEFAULT_INCLUDE_PATTERN = "/v1/.*";

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "swagger.");
    }

    @Bean
    public SwaggerSpringMvcPlugin swaggerSpringMvcPlugin(SpringSwaggerConfig springSwaggerConfig) {
        return new SwaggerSpringMvcPlugin(springSwaggerConfig)
                .apiInfo(apiInfo())
                .genericModelSubstitutes(ResponseEntity.class)
                .includePatterns(DEFAULT_INCLUDE_PATTERN);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                propertyResolver.getProperty("title"),
                propertyResolver.getProperty("description"),
                propertyResolver.getProperty("termsOfServiceUrl"),
                propertyResolver.getProperty("contact"),
                propertyResolver.getProperty("license"),
                propertyResolver.getProperty("licenseUrl"));
    }

}