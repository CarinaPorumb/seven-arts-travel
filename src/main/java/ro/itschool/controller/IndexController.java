package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.repository.ArtEventRepository;
import ro.itschool.repository.ArtObjectRepository;

@Controller
public class IndexController {
    @Autowired
    ArtEventRepository artEventRepository;
    @Autowired
    ArtObjectRepository artObjectRepository;

    @RequestMapping(value = {"/index"})
    public String index() {
        return "index.html";
    }

    @GetMapping("/search-list")
    public String getArtEventList(Model model, String keyword) {
        model.addAttribute("artevents", artEventRepository.searchArtEvent(keyword));
        model.addAttribute("artobjects", artObjectRepository.searchArtObject(keyword));
        return "/search";
    }
}