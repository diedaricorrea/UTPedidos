package com.example.Ejemplo.routes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Routes {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

//    @GetMapping("/carShop")
//    public String carShop(){
//        return "carShop";
//    }

    @GetMapping("/panelAdmin")
    public String panelAdmin(){
        return "panelAdmin";
    }
}
