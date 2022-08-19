package ro.itschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IntroController {

    @RequestMapping(value = {"/intro"})
    public String intro() {
        return "intro.html";
    }
}




