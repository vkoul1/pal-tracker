package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    public String message;

    public WelcomeController( @Value("${WELCOME_MESSAGE}") String msg){
        this.message = msg;
    }

    @GetMapping("/")
    public String sayHello() {
        return message;
    }
}