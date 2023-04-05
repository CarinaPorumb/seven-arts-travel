package ro.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.project.entity.ArtObject;
import ro.project.model.ArtObjectDTO;
import ro.project.service.ArtObjectService;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ArtObjectController {

    private final ArtObjectService artObjectService;

    @GetMapping("/artobject-list")
    public String getArtObjects(Model model, String keyword) throws Exception {
        model.addAttribute("artobjects", artObjectService.searchArtObject(keyword));
        return "/artobject";
    }

    @GetMapping("/save-artobject")
    public String saveArtObject1(Model model) {
        model.addAttribute("artobject", new ArtObject());
        return "save-artobject";
    }

    @PostMapping("/save-artobject")
    public String saveArtObject2(@ModelAttribute ArtObjectDTO artObject, Model model) {
        model.addAttribute("artobject", artObject);
        artObjectService.save(artObject);
        return "redirect:/artobject-list?keyword=";
    }

    @GetMapping(path = "/update-artobject/{id}")
    public String updateArtObject(@PathVariable("id") Integer id, Model model) {
        Optional<ArtObjectDTO> artObject = artObjectService.getById(id);
        model.addAttribute("artobject", artObject);
        return "update-artobject";
    }

    @RequestMapping(path = "/delete-artobject/{id}")
    public String deleteArtObject(@PathVariable("id") Integer id) throws Exception {
        artObjectService.deleteById(id);
        return "redirect:/artobject-list?keyword=";
    }

    @GetMapping("/architecture")
    public String displayArchitecture(Model model, String keyword) throws Exception {
        model.addAttribute("artobjects", artObjectService.displayArchitecture(keyword));
        return "/architecture-list";
    }

    @GetMapping("/sculpture")
    public String displaySculpture(Model model, String keyword) throws Exception {
        model.addAttribute("artobjects", artObjectService.displaySculpture(keyword));
        return "/sculpture-list";
    }

    @GetMapping("/literature")
    public String displayLiterature(Model model, String keyword) throws Exception {
        model.addAttribute("artobjects", artObjectService.displayLiterature(keyword));
        return "/literature-list";
    }

    @GetMapping("/painting")
    public String displayPainting(Model model, String keyword) throws Exception {
        model.addAttribute("artobjects", artObjectService.displayPainting(keyword));
        return "/painting-list";
    }

}