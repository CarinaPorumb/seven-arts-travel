package ro.itschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.entity.User;

@Controller
public class IntroController {

    @RequestMapping(value = {"/intro", "/"})
    public String intro(Model model) {
        User user = new User();
        user.setEnabled(false);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        model.addAttribute("user", user);

        return "intro";
    }
}




