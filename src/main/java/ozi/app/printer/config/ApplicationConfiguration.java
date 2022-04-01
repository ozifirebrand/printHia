package ozi.app.printer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {
     public void addResourceHandlers(final ResourceHandlerRegistry registry) {
         registry.addResourceHandler("/resources/").addResourceLocations("classpath:/resources/static/");

    }
}
