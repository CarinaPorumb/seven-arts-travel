package ro.itschool.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Controller
public class BalletAndTheatreController {

    @Autowired
    BalletAndTheatreRepository balletAndTheatreRepository;

    @GetMapping("/ballet-theatre")
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
        return "redirect:/balletAndTheatre";
    }

    @GetMapping("/updateBalletAndTheatre/{name}")
    public String updateBalletAndTheatre(@PathVariable String name) throws BalletAndTheatreNotFound {
        Optional.ofNullable(balletAndTheatreRepository.findByName(name)).orElseThrow(BalletAndTheatreNotFound::new);
        return "updateBalletAndTheatre";
    }

    @DeleteMapping("/deleteBalletAndTheatre/{name}")
    public String deleteBalletAndTheatre(@PathVariable String name) throws BalletAndTheatreNotFound {
        Optional.ofNullable(balletAndTheatreRepository.findByName(name)).orElseThrow(BalletAndTheatreNotFound::new);
        balletAndTheatreRepository.deleteByName(name);
        return "redirect:/balletAndTheatre";
    }

    //?
    @PostMapping("/addBalletAndTheatreToUser")
    public String addBalletAndTheatreToUser(@ModelAttribute BalletAndTheatre balletAndTheatre, User user, Model model) {
        model.addAttribute("balletAndTheatreObject", balletAndTheatre);
        user.setBalletAndTheatreSet(Collections.singleton(balletAndTheatre));
        balletAndTheatreRepository.save(balletAndTheatre);
        return "redirect:/balletAndTheatre";
    }
}
