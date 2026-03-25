package com.jsport.backend_ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Backend de JS-PORT funcionando correctamente en Render!";
    }
}
