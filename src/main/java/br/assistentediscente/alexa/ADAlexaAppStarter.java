package br.assistentediscente.alexa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ADAlexaAppStarter extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ADAlexaAppStarter.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ADAlexaAppStarter.class, args);
    }
}