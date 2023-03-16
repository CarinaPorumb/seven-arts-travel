package ro.itschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.ArtEvent;
import ro.itschool.exception.ArtEventNotFound;
import ro.itschool.service.ArtEventService;

import java.util.Optional;

@Controller
public class ArtEventController {

    private final ArtEventService artEventService;

    public ArtEventController(ArtEventService artEventService) {
        this.artEventService = artEventService;
    }

    @GetMapping("/artevent-list")
    public String getArtEventList(Model model, String keyword) throws Exception {
        model.addAttribute("artevents", artEventService.searchArtEvent(keyword));
        return "/artevent";
    }

    @GetMapping("/save-artevent")
    public String saveArtEvent1(Model model) {
        model.addAttribute("artevent", new ArtEvent());
        return "save-artevent";
    }

    @PostMapping("/save-artevent")
    public String saveArtEvent2(@ModelAttribute ArtEvent artEvent, Model model) throws Exception {
        model.addAttribute("artevent", artEvent);
        artEventService.save(artEvent);
        return "redirect:/artevent-list?keyword=";
    }

    @GetMapping(path = "/update-artevent/{name}")
    public String updateArtEvent(@PathVariable("name") String name, Model model) throws Exception {
        ArtEvent artEvent = Optional.ofNullable(artEventService.findByName(name)).orElseThrow(ArtEventNotFound::new);
        model.addAttribute("artevent", artEvent);
        return "update-artEvent";
    }

    @RequestMapping(path = "/delete-artevent/{name}")
    public String deleteArtEvent(@PathVariable("name") String name) throws Exception {
        artEventService.deleteByName(name);
        return "redirect:/artevent-list?keyword=";
    }

    @GetMapping("/music")
    public String displayMusic(Model model, String keyword) throws Exception {
        model.addAttribute("artevents", artEventService.displayMusic(keyword));
        return "/music-list";
    }

    @GetMapping("/cinema")
    public String displayCinema(Model model, String keyword) throws Exception {
        model.addAttribute("artevents", artEventService.displayCinema(keyword));
        return "/cinema-list";
    }

    @GetMapping("/ballet-theatre")
    public String displayBalletAndTheatre(Model model, String keyword) throws Exception {
        model.addAttribute("artevents", artEventService.displayBalletAndTheatre(keyword));
        return "/ballet-theatre-list";
    }
}