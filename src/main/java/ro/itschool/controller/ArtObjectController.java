package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.ArtObject;
import ro.itschool.exception.ArtObjectNotFound;
import ro.itschool.repository.ArtObjectRepository;

import java.util.Optional;

@Controller
public class ArtObjectController {

    @Autowired
    private ArtObjectRepository artObjectRepository;

    @GetMapping("/artobject-list")
    public String getArtObjects(Model model, String keyword) {
        model.addAttribute("artobjects", artObjectRepository.searchArtObject(keyword));
        return "/artobject";
    }

    @GetMapping("/save-artobject")
    public String saveArtObject1(Model model) {
        model.addAttribute("artobject", new ArtObject());
        return "save-artobject";
    }

    @PostMapping("/save-artobject")
    public String saveArtObject2(@ModelAttribute ArtObject artObject, Model model) {
        model.addAttribute("artobject", artObject);
        artObjectRepository.save(artObject);
        return "redirect:/artobject-list?keyword=";
    }

    @GetMapping(path = "/update-artobject/{name}")
    public String updateArtObject(@PathVariable("name") String name, Model model) throws ArtObjectNotFound {
        ArtObject artObject = Optional.ofNullable(artObjectRepository.findByName(name)).orElseThrow(ArtObjectNotFound::new);
        model.addAttribute("artobject", artObject);
        return "update-artobject";
    }

    @RequestMapping(path = "/delete-artobject/{name}")
    public String deleteArtObject(@PathVariable("name") String name) {
        artObjectRepository.deleteByName(name);
        return "redirect:/artobject-list?keyword=";
    }

    @GetMapping("/architecture")
    public String displayArchitecture(Model model, String keyword) {
        model.addAttribute("artobjects", artObjectRepository.displayArchitecture(keyword));
        return "/architecture-list";
    }

    @GetMapping("/sculpture")
    public String displaySculpture(Model model, String keyword) {
        model.addAttribute("artobjects", artObjectRepository.displaySculpture(keyword));
        return "/sculpture-list";
    }

    @GetMapping("/literature")
    public String displayLiterature(Model model, String keyword) {
        model.addAttribute("artobjects", artObjectRepository.displayLiterature(keyword));
        return "/literature-list";
    }

    @GetMapping("/painting")
    public String displayPainting(Model model, String keyword) {
        model.addAttribute("artobjects", artObjectRepository.displayPainting(keyword));
        return "/painting-list";
    }

}