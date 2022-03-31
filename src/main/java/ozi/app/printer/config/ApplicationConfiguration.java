package ozi.app.printer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ComponentScan("ozi.app.printer.web.controllers")
@EnableWebMvc
@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {
    @Bean
    public InternalResourceViewResolver jspViewResolver(){
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF");
        bean.setSuffix("jsp");
        return bean;
    }
}
