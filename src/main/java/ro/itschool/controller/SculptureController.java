package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Sculpture;
import ro.itschool.entity.User;
import ro.itschool.exception.SculptureNotFound;
import ro.itschool.repository.SculptureRepository;
import ro.itschool.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@Controller
public class SculptureController {

    @Autowired
    SculptureRepository sculptureRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/all-sculpture-list")
    public String getSculptures(Model model, String keyword) {
        model.addAttribute("sculptures", sculptureRepository.searchSculpture(keyword));
        return "/sculpture";
    }

    @GetMapping("/save-sculpture")
    public String saveSculpture1(Model model) {
        model.addAttribute("sculpture", new Sculpture());
        return "save-sculpture";
    }

    @PostMapping("/save-sculpture")
    public String saveSculpture2(@ModelAttribute Sculpture sculpture, Model model) {
        model.addAttribute("sculpture", sculpture);
        sculptureRepository.save(sculpture);
        return "redirect:/all-sculpture-list";
    }

    @GetMapping(path = "/update-sculpture/{name}")
    public String updateSculpture(@PathVariable("name") String name, Model model) throws SculptureNotFound {
        Sculpture sculpture = Optional.ofNullable(sculptureRepository.findByName(name)).orElseThrow(SculptureNotFound::new);
        model.addAttribute("sculpture", sculpture);
        return "update-sculpture";
    }

    @RequestMapping("/delete-sculpture/{name}")
    public String deleteSculpture(@PathVariable("name") String name) {
        sculptureRepository.deleteByName(name);
        return "redirect:/all-sculpture-list";
    }

    //?
    @RequestMapping("/addSculptureToUser")
    public String addSculptureToUser(@ModelAttribute Sculpture sculpture, User user, Model model) {
        model.addAttribute("sculptureObject", sculpture);
        Set<Sculpture> sculptures = user.getSculptures();
        sculptures.add(sculpture);
        user.setSculptures(sculptures);
        userRepository.save(user);
        return "redirect:/myList";
    }
}