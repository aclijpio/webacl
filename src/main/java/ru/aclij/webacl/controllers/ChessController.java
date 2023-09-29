package ru.aclij.webacl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChessController {
    @GetMapping("/chess")
    public String chess(){
        return "/chess/chess";
    }
    @GetMapping("/chessmap")
    public String chessMap(){
        return "/chess/chess";
    }
}
