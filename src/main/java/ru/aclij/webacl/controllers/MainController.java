package ru.aclij.webacl.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
public class MainController {
    @GetMapping()
    public String index(){
        return "/pages/index";
    }
    @GetMapping("/sec")
    public String secured(){
        return "/pages/chess/game";
    }
    @GetMapping("/admin")
    public String admin(){
        return "/pages/chess/game";
    }
}