package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.BalletAndTheatre;
import ro.itschool.entity.User;
import ro.itschool.exception.BalletAndTheatreNotFound;
import ro.itschool.repository.BalletAndTheatreRepository;
import ro.itschool.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@Controller
public class BalletAndTheatreController {

    @Autowired
    BalletAndTheatreRepository balletAndTheatreRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all-ballet-theatre-list")
    public String getBalletAndTheatreList(Model model, String keyword) {
        model.addAttribute("balletAndTheatreList", balletAndTheatreRepository.searchBalletAndTheatre(keyword));
        return "/ballet-theatre";
    }

    @GetMapping("/save-balletAndTheatre")
    public String saveBalletAndTheatre1(Model model) {
        model.addAttribute("balletAndTheatreObject", new BalletAndTheatre());
        return "save-balletAndTheatre";
    }

    @PostMapping("/save-balletAndTheatre")
    public String saveBalletAndTheatre2(@ModelAttribute BalletAndTheatre balletAndTheatre, Model model) {
        model.addAttribute("balletAndTheatreObject", balletAndTheatre);
        balletAndTheatreRepository.save(balletAndTheatre);
        return "redirect:/all-ballet-theatre-list";
    }

    @GetMapping(path = "/update-balletAndTheatre/{name}")
    public String updateBalletAndTheatre(@PathVariable("name") String name, Model model) throws BalletAndTheatreNotFound {
        BalletAndTheatre balletAndTheatre = Optional.ofNullable(balletAndTheatreRepository.findByName(name)).orElseThrow(BalletAndTheatreNotFound::new);
        model.addAttribute("balletAndTheatre", balletAndTheatre);
        return "update-BalletAndTheatre";
    }

    @RequestMapping(path = "/delete-balletAndTheatre/{name}")
    public String deleteBalletAndTheatre(@PathVariable("name") String name) {
        balletAndTheatreRepository.deleteByName(name);
        return "redirect:/all-ballet-theatre-list";
    }

    //?
    @RequestMapping("/addBalletAndTheatreToUser")
    public String addBalletAndTheatreToUser(@ModelAttribute BalletAndTheatre balletAndTheatre, User user, Model model) {
        model.addAttribute("balletAndTheatreObject", balletAndTheatre);
        Set<BalletAndTheatre> balletAndTheatreSet = user.getBalletAndTheatreSet();
        balletAndTheatreSet.add(balletAndTheatre);
        user.setBalletAndTheatreSet(balletAndTheatreSet);
        userRepository.save(user);
        return "redirect:/all-ballet-theatre-list";
    }
}
