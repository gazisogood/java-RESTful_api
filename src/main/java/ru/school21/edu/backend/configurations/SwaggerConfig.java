package ru.school21.edu.backend.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080/api/v1");
        server.setDescription("Backend. Project 2");


        Contact myContact = new Contact();
        myContact.setName("Arboriob");
        myContact.setEmail("arboriob@student.21-school.ru");

        Info information = new Info()
                .title("Store API")
                .version("1.0")
                .description("This API exposes endpoints to manage simple store.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
