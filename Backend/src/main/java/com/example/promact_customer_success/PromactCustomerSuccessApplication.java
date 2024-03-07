package com.example.promact_customer_success;

import com.example.promact_customer_success.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
public class    PromactCustomerSuccessApplication {

    public static void main(String[] args) {

        SpringApplication.run(PromactCustomerSuccessApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/*").allowedOrigins("http://localhost:3000");
                registry.addMapping("/**")
                        .allowedOrigins("*") // or specify your origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
    @GetMapping("/index")

    public String  home() {
        return  String.format("done");
    }

}
