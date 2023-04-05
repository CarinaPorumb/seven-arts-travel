package ro.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.project.entity.ArtEvent;
import ro.project.model.ArtEventDTO;
import ro.project.service.ArtEventService;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ArtEventController {

    private final ArtEventService artEventService;

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
    public String saveArtEvent2(@ModelAttribute ArtEventDTO artEvent, Model model) throws Exception {
        model.addAttribute("artevent", artEvent);
        artEventService.save(artEvent);
        return "redirect:/artevent-list?keyword=";
    }

    @GetMapping(path = "/update-artevent/{id}")
    public String updateArtEvent(@PathVariable("id") Integer id, Model model) {
        Optional<ArtEventDTO> artEvent = artEventService.getById(id);
        model.addAttribute("artevent", artEvent);
        return "update-artEvent";
    }

    @RequestMapping(path = "/delete-artevent/{id}")
    public String deleteArtEvent(@PathVariable("id") Integer id) throws Exception {
        artEventService.deleteById(id);
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