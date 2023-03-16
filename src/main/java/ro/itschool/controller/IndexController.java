package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.service.ArtEventService;
import ro.itschool.service.ArtObjectService;

@Controller
public class IndexController {
    @Autowired
    ArtEventService artEventService;
    @Autowired
    ArtObjectService artObjectService;

    @RequestMapping(value = {"/index"})
    public String index() {
        return "index.html";
    }

    @GetMapping("/search-list")
    public String getArtEventList(Model model, String keyword) throws Exception {
        model.addAttribute("artevents", artEventService.searchArtEvent(keyword));
        model.addAttribute("artobjects", artObjectService.searchArtObject(keyword));
        return "/search";
    }
}