package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Literature;
import ro.itschool.entity.User;
import ro.itschool.exception.LiteratureNotFound;
import ro.itschool.repository.LiteratureRepository;

import java.util.Collections;
import java.util.Optional;

@Controller
public class LiteratureController {

    @Autowired
    LiteratureRepository literatureRepository;

    @GetMapping("/literature")
    public String getLiteratures(Model model, String keyword) {
        model.addAttribute("literatures", literatureRepository.searchLiterature(keyword));
        return "/literature";
    }

    @GetMapping("/saveLiterature")
    public String saveLiterature1(Model model) {
        model.addAttribute("literatureObject", new Literature());
        return "saveLiterature";
    }

    @PostMapping("/saveLiterature")
    public String saveLiterature2(@ModelAttribute Literature literature, Model model) {
        model.addAttribute("literatureObject", literature);
        literatureRepository.save(literature);
        return "redirect:/literature";
    }

    @GetMapping("/updateLiterature/{name}")
    public String updateLiterature(@PathVariable String name) throws LiteratureNotFound {
        Optional.ofNullable(literatureRepository.findByName(name)).orElseThrow(LiteratureNotFound::new);
        return "updateLiterature";
    }

    @DeleteMapping("/deleteLiterature/{name}")
    public String deleteLiterature(@PathVariable String name) throws LiteratureNotFound {
        Optional.ofNullable(literatureRepository.findByName(name)).orElseThrow(LiteratureNotFound::new);
        literatureRepository.deleteByName(name);
        return "redirect:/literature";
    }

    //?
    @PostMapping("/addLiteratureToUser")
    public String addLiteratureToUser(@ModelAttribute Literature literature, User user, Model model) {
        model.addAttribute("literatureObject", literature);
        user.setLiteratureSet(Collections.singleton(literature));
        literatureRepository.save(literature);
        return "redirect:/literature";
    }
}
