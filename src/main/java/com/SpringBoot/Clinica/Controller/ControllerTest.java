package com.SpringBoot.Clinica.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {
    @GetMapping(value = "/test")
    public String Test(){
        return "Test";
    }
}
