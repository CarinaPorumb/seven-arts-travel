package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.BalletAndTheatre;
import ro.itschool.entity.User;
import ro.itschool.exception.BalletAndTheatreNotFound;
import ro.itschool.repository.BalletAndTheatreRepository;

import java.util.Collections;
import java.util.Optional;

@Controller
public class BalletAndTheatreController {

    @Autowired
    BalletAndTheatreRepository balletAndTheatreRepository;

    @GetMapping("/all-ballet-theatre-list")
    public String getBalletAndTheatreList(Model model, String keyword) {
        model.addAttribute("balletAndTheatreList", balletAndTheatreRepository.searchBalletAndTheatre(keyword));
        return "/ballet-theatre";
    }

    @GetMapping("/saveBalletAndTheatre")
    public String saveBalletAndTheatre1(Model model) {
        model.addAttribute("balletAndTheatreObject", new BalletAndTheatre());
        return "saveBalletAndTheatre";
    }

    @PostMapping("/saveBalletAndTheatre")
    public String saveBalletAndTheatre2(@ModelAttribute BalletAndTheatre balletAndTheatre, Model model) {
        model.addAttribute("balletAndTheatreObject", balletAndTheatre);
        balletAndTheatreRepository.save(balletAndTheatre);
        return "redirect:/all-ballet-theatre-list";
    }

    @GetMapping(path = "/updateBalletAndTheatre/{name}")
    public String updateBalletAndTheatre(@PathVariable("name") String name, Model model) throws BalletAndTheatreNotFound {
        Optional.ofNullable(balletAndTheatreRepository.findByName(name)).orElseThrow(BalletAndTheatreNotFound::new);
        model.addAttribute("balletAndTheatre", balletAndTheatreRepository.findByName(name).get());
        return "updateBalletAndTheatre";
    }

    @RequestMapping(path = "/deleteBalletAndTheatre/{name}")
    public String deleteBalletAndTheatre(@PathVariable("name") String name) {
        balletAndTheatreRepository.deleteByName(name);
        return "redirect:/all-ballet-theatre-list";
    }

    //?
    @PostMapping("/addBalletAndTheatreToUser")
    public String addBalletAndTheatreToUser(@ModelAttribute BalletAndTheatre balletAndTheatre, User user, Model model) {
        model.addAttribute("balletAndTheatreObject", balletAndTheatre);
        user.setBalletAndTheatreSet(Collections.singleton(balletAndTheatre));
        balletAndTheatreRepository.save(balletAndTheatre);
        return "redirect:/all-ballet-theatre-list";
    }
}
