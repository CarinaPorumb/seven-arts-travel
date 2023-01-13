package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Literature;
import ro.itschool.entity.User;
import ro.itschool.exception.LiteratureNotFound;
import ro.itschool.repository.LiteratureRepository;
import ro.itschool.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@Controller
public class LiteratureController {

    @Autowired
    LiteratureRepository literatureRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/all-literature-list")
    public String getLiteratures(Model model, String keyword) {
        model.addAttribute("literatures", literatureRepository.searchLiterature(keyword));
        return "/literature";
    }

    @GetMapping("/save-literature")
    public String saveLiterature1(Model model) {
        model.addAttribute("literatureObj", new Literature());
        return "save-literature";
    }

    @PostMapping("/save-literature")
    public String saveLiterature2(@ModelAttribute Literature literature, Model model) {
        model.addAttribute("literatureObj", literature);
        literatureRepository.save(literature);
        return "redirect:/all-literature-list";
    }

    @GetMapping(path = "/update-literature/{name}")
    public String updateLiterature(@PathVariable("name") String name, Model model) throws LiteratureNotFound {
        Literature literature = Optional.ofNullable(literatureRepository.findByName(name)).orElseThrow(LiteratureNotFound::new);
        model.addAttribute("literature", literature);
        return "update-literature";
    }

    @RequestMapping(path = "/delete-literature/{name}")
    public String deleteLiterature(@PathVariable("name") String name) {
        literatureRepository.deleteByName(name);
        return "redirect:/all-literature-list";
    }

    //?
    @RequestMapping("/addLiteratureToUser")
    public String addLiteratureToUser(@ModelAttribute Literature literature, User user, Model model) {
        model.addAttribute("literature", literature);
        Set<Literature> literature1 = user.getLiteratureSet();
        literature1.add(literature);
        user.setLiteratureSet(literature1);
        userRepository.save(user);
        return "redirect:/myList";
    }
}