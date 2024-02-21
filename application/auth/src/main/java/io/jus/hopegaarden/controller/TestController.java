package io.jus.hopegaarden.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test!!";
    }

    @GetMapping("/auth/test")
    public String authTest() {
        return "test!!";
    }
}
