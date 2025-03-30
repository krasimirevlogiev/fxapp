package com.example.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiDocsController {
    @GetMapping("api/docs")
    public String redirectToSwaggerUi() {
        return "redirect:/swagger-ui/index.html";
    }
}
