package ru.aclij.webacl.apps.chess;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
@Controller
@RequiredArgsConstructor
public class ChessController {
    private final ChessService service;

    @GetMapping("/chess")
    public String startChess(Model model, Principal principal){
        model.addAttribute("pr", principal);
        return "/pages/chess/start";
    }
    @GetMapping("/chess/{id}")
    public String chess(@PathVariable("id") String id){
        return "/pages/chess/game";
    }
}
