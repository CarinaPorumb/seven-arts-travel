package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ro.itschool.service.ArchitectureService;
import ro.itschool.service.UserService;

@Controller
public class ArchitectureController {

    @Autowired
    UserService userService;

    @Autowired
    ArchitectureService architectureService;


}
