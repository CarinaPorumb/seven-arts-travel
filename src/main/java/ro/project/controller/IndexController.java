package ro.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.project.service.ArtEventService;
import ro.project.service.ArtObjectService;
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final ArtEventService artEventService;
    private final ArtObjectService artObjectService;

    @RequestMapping(value = {"/index"})
    public String index() {
        return "index.html";
    }

    @GetMapping("/search-list")
    public String getArtEventList(Model model, String keyword) {
        model.addAttribute("artevents", artEventService.searchArtEvent(keyword));
        model.addAttribute("artobjects", artObjectService.searchArtObject(keyword));
        return "/search";
    }
}