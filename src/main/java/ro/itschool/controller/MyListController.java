package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.exception.UserNotFound;
import ro.itschool.service.UserService;

@Controller
public class MyListController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/myList"})
    public String myList(Model model) throws UserNotFound {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("architecture", userService.findUserByUserName(auth.getName()).getArtEventSet());
        return "myList";
    }
}