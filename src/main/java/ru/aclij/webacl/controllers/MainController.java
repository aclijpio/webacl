package ru.aclij.webacl.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.aclij.webacl.security.dtos.JwtRequest;

import java.security.Principal;

@Controller
public class MainController {
    @GetMapping()
    public String index(@ModelAttribute JwtRequest jwtRequest){
        return "/pages/index";
    }

}