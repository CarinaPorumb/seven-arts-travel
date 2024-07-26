//package ro.project.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ro.project.service.entity.ArtEventService;
//import ro.project.service.entity.ArtWorkService;
//@RequiredArgsConstructor
//@Controller
//public class IndexController {
//
//    private final ArtEventService artEventService;
//    private final ArtWorkService artWorkService;
//
//    @RequestMapping(value = {"/index"})
//    public String index() {
//        return "index.html";
//    }
//
//    @GetMapping("/search-list")
//    public String getArtEventList(Model model, String keyword) {
//        model.addAttribute("artevents", artEventService.searchArtEvents(keyword));
//        model.addAttribute("artobjects", artWorkService.searchArtObject(keyword));
//        return "/search";
//    }
//}