package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Painting;
import ro.itschool.entity.User;
import ro.itschool.exception.PaintingNotFound;
import ro.itschool.repository.PaintingRepository;

import java.util.Collections;
import java.util.Optional;

@Controller
public class PaintingController {

    @Autowired
    PaintingRepository paintingRepository;

    @GetMapping("/painting")
    public String getAllPaintings(Model model, String keyword) {
        model.addAttribute("paintings", paintingRepository.searchPaintings(keyword));
        return "/painting";
    }

    @GetMapping("/savePainting")
    public String savePainting1(Model model) {
        model.addAttribute("paintingObject", new Painting());
        return "savePainting";
    }

    @PostMapping("/savePainting")
    public String savePainting2(@ModelAttribute Painting painting, Model model) {
        model.addAttribute("paintingObject", painting);
        paintingRepository.save(painting);
        return "redirect:/painting";
    }

    @GetMapping("/updatePainting/{name}")
    public String updatePainting(@PathVariable String name) throws PaintingNotFound {
        Optional.ofNullable(paintingRepository.findByName(name)).orElseThrow(PaintingNotFound::new);
        return "updatePainting";
    }

    @DeleteMapping("/deletePainting/{name}")
    public String deletePainting(@PathVariable String name) throws PaintingNotFound {
        Optional.ofNullable(paintingRepository.findByName(name)).orElseThrow(PaintingNotFound::new);
        paintingRepository.deleteByName(name);
        return "redirect:/painting";
    }

    //?
    @PostMapping("/addPaintingToUser")
    public String addPaintingToUser(@ModelAttribute Painting painting, User user, Model model) {
        model.addAttribute("paintingObject", painting);
        user.setPaintings(Collections.singleton(painting));
        paintingRepository.save(painting);
        return "redirect:/painting";
    }
}
