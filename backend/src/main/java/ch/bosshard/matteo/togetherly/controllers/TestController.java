package ch.bosshard.matteo.togetherly.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-api")
public class TestController {

    @GetMapping("/ping")
    public String ping() {
        return "pong from backend";
    }
}
