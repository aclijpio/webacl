package ru.aclij.webacl.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.aclij.webacl.controllers.dtos.AppErrorException;

@ControllerAdvice
public class ExceptionController {

   @ExceptionHandler(AppErrorException.class)
    public ModelAndView authException(AppErrorException e){
       ModelAndView model = new ModelAndView("/unauth");
       model.addObject(
               "error",
               e.getAppError()
       );
        return model;
    }

}
