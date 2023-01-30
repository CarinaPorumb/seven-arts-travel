package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.ArtEvent;
import ro.itschool.exception.ArtEventNotFound;
import ro.itschool.repository.ArtEventRepository;

import java.util.Optional;

@Controller
public class ArtEventController {

    @Autowired
    private ArtEventRepository artEventRepository;

    @GetMapping("/artevent-list")
    public String getArtEventList(Model model, String keyword) {
        model.addAttribute("artevents", artEventRepository.searchArtEvent(keyword));
        return "/artevent";
    }

    @GetMapping("/save-artevent")
    public String saveArtEvent1(Model model) {
        model.addAttribute("artevent", new ArtEvent());
        return "save-artevent";
    }

    @PostMapping("/save-artevent")
    public String saveArtEvent2(@ModelAttribute ArtEvent artEvent, Model model) {
        model.addAttribute("artevent", artEvent);
        artEventRepository.save(artEvent);
        return "redirect:/artevent-list?keyword=";
    }

    @GetMapping(path = "/update-artevent/{name}")
    public String updateArtEvent(@PathVariable("name") String name, Model model) throws ArtEventNotFound {
        ArtEvent artEvent = Optional.ofNullable(artEventRepository.findByName(name)).orElseThrow(ArtEventNotFound::new);
        model.addAttribute("artEvent", artEvent);
        return "update-artEvent";
    }

    @RequestMapping(path = "/delete-artevent/{name}")
    public String deleteArtEvent(@PathVariable("name") String name) {
        artEventRepository.deleteByName(name);
        return "redirect:/artevent-list?keyword=";
    }

    @GetMapping("/music")
    public String displayMusic(Model model, String keyword) {
        model.addAttribute("artevents", artEventRepository.displayMusic(keyword));
        return "/music-list";
    }

    @GetMapping("/cinema")
    public String displayCinema(Model model, String keyword) {
        model.addAttribute("artevents", artEventRepository.displayCinema(keyword));
        return "/cinema-list";
    }

    @GetMapping("/ballet-theatre")
    public String displayBalletAndTheatre(Model model, String keyword) {
        model.addAttribute("artevents", artEventRepository.displayBalletAndTheatre(keyword));
        return "/ballet-theatre-list";
    }
}