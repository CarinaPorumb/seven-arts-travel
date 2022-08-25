package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Sculpture;
import ro.itschool.entity.User;
import ro.itschool.exception.SculptureNotFound;
import ro.itschool.repository.SculptureRepository;

import java.util.Collections;
import java.util.Optional;

@Controller
public class SculptureController {

    @Autowired
    SculptureRepository sculptureRepository;

    @GetMapping("/sculpture")
    public String getSculptures(Model model, String keyword) {
        model.addAttribute("sculptures", sculptureRepository.searchSculpture(keyword));
        return "/sculpture";
    }

    @GetMapping("/saveSculpture")
    public String saveSculpture1(Model model) {
        model.addAttribute("sculptureObject", new Sculpture());
        return "saveSculpture";
    }

    @PostMapping("/saveSculpture")
    public String saveSculpture2(@ModelAttribute Sculpture sculpture, Model model) {
        model.addAttribute("sculptureObject", sculpture);
        sculptureRepository.save(sculpture);
        return "redirect:/sculpture";
    }

    @GetMapping("/updateSculpture/{name}")
    public String updateSculpture(@PathVariable String name) throws SculptureNotFound {
        Optional.ofNullable(sculptureRepository.findByName(name)).orElseThrow(SculptureNotFound::new);
        return "updateSculpture";
    }

    @DeleteMapping("/deleteSculpture/{name}")
    public String deleteSculpture(@PathVariable String name) throws SculptureNotFound {
        Optional.ofNullable(sculptureRepository.findByName(name)).orElseThrow(SculptureNotFound::new);
        sculptureRepository.deleteByName(name);
        return "redirect:/sculpture";
    }

    //?
    @PostMapping("/addSculptureToUser")
    public String addSculptureToUser(@ModelAttribute Sculpture sculpture, User user, Model model) {
        model.addAttribute("sculptureObject", sculpture);
        user.setSculptures(Collections.singleton(sculpture));
        sculptureRepository.save(sculpture);
        return "redirect:/sculpture";
    }
}
