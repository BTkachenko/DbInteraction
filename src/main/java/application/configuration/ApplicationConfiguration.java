package application.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer
{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/style/**")
                .addResourceLocations("classpath:/static/styles/");
        registry.addResourceHandler("/script/**")
                .addResourceLocations("classpath:/static/scripts/");
    }
}