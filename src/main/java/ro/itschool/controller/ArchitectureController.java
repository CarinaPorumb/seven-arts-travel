package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Architecture;
import ro.itschool.entity.User;
import ro.itschool.exception.ArchitectureNotFound;
import ro.itschool.repository.ArchitectureRepository;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.UserService;

import java.util.Optional;

@Controller
public class ArchitectureController {

    @Autowired
    ArchitectureRepository architectureRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/all-architecture-list")
    public String getArchitectures(Model model, String keyword) {
        model.addAttribute("architectures", architectureRepository.searchArchitecture(keyword));
        return "/architecture";
    }

    @GetMapping("/save-architecture")
    public String saveArchitecture1(Model model) {
        model.addAttribute("architectureObject", new Architecture());
        return "save-architecture";
    }

    @PostMapping("/save-architecture")
    public String saveArchitecture2(@ModelAttribute Architecture architecture, Model model) {
        model.addAttribute("architectureObject", architecture);
        architectureRepository.save(architecture);
        return "redirect:/all-architecture-list?keyword=";
    }

    @GetMapping(path = "/update-architecture/{name}")
    public String updateArchitecture(@PathVariable("name") String name, Model model) throws ArchitectureNotFound {
        Architecture architecture = Optional.ofNullable(architectureRepository.findByName(name)).orElseThrow(ArchitectureNotFound::new);
        model.addAttribute("architecture", architecture);
        return "update-architecture";
    }

    @RequestMapping(path = "/delete-architecture/{name}")
    public String deleteArchitecture(@PathVariable("name") String name) {
        architectureRepository.deleteByName(name);
        return "redirect:/all-architecture-list";
    }

    //?
    @RequestMapping("/add-architecture/{name}")
    public String addArchitectureToUser(@PathVariable("name") String name) {
        final Optional<User> user = Optional.ofNullable(userRepository.findByUsernameIgnoreCase(name));
        if (user.isPresent()) {
            final Architecture architecture = architectureRepository.findByName(name);
            user.get().getArchitectureSet().add(architecture);
            userService.updateUser(user.get());
            return "redirect:/my/List";
        }
        return ("USER not found for this id : " + user);
    }

}