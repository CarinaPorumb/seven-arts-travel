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

import java.util.Optional;
import java.util.Set;

@Controller
public class ArchitectureController {

    @Autowired
    ArchitectureRepository architectureRepository;

    @Autowired
    UserRepository userRepository;

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
        return "redirect:/all-architecture-list";
    }

    @GetMapping(path = "/update-architecture/{name}")
    public String updateArchitecture(@PathVariable("name") String name, Model model) throws ArchitectureNotFound {
        Optional.ofNullable(architectureRepository.findByName(name)).orElseThrow(ArchitectureNotFound::new);
        model.addAttribute("architecture", architectureRepository.findByName(name));
        return "update-architecture";
    }

    @RequestMapping(path = "/delete-architecture/{name}")
    public String deleteArchitecture(@PathVariable("name") String name) {
        architectureRepository.deleteByName(name);
        return "redirect:/all-architecture-list";
    }

    //?
    @RequestMapping("/addArchitectureToUser")
    public String addArchitectureToUser(@ModelAttribute Architecture architecture, User user, Model model) {
        model.addAttribute("architectureObject", architecture);
        Set<Architecture> arch = user.getArchitectureSet();
        arch.add(architecture);
        user.setArchitectureSet(arch);
        userRepository.save(user);
        return "redirect:/myList";
    }

}