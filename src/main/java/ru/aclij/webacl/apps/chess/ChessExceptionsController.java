package ru.aclij.webacl.apps.chess;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.aclij.webacl.apps.chess.exceptions.ChessSessionPlayersFullException;

@ControllerAdvice()
public class ChessExceptionsController {
    @ExceptionHandler(ChessSessionPlayersFullException.class)
    public ModelAndView ChessSessionFull(ChessSessionPlayersFullException e){
        ModelAndView modelAndView = new ModelAndView("/pages/chess/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }
}

