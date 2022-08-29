package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Painting;
import ro.itschool.entity.User;
import ro.itschool.exception.PaintingNotFound;
import ro.itschool.exception.UserNotFound;
import ro.itschool.repository.PaintingRepository;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.UserService;

import java.util.Optional;

@Controller
public class PaintingController {

    @Autowired
    PaintingRepository paintingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/all-painting-list")
    public String getAllPaintings(Model model, String keyword) {
        model.addAttribute("paintings", paintingRepository.searchPaintings(keyword));
        return "/painting";
    }

    @GetMapping("/save-painting")
    public String savePainting1(Model model) {
        model.addAttribute("painting", new Painting());
        return "save-Painting";
    }

    @PostMapping("/save-painting")
    public String savePainting2(@ModelAttribute Painting painting, Model model) {
        model.addAttribute("painting", painting);
        paintingRepository.save(painting);
        return "redirect:/all-painting-list";
    }

    @GetMapping(path = "/update-painting/{name}")
    public String updatePainting(@PathVariable("name") String name, Model model) throws PaintingNotFound {
        Optional.ofNullable(paintingRepository.findByName(name)).orElseThrow(PaintingNotFound::new);
        model.addAttribute("painting", paintingRepository.findByName(name));
        return "update-painting";
    }

    @RequestMapping("/delete-painting/{name}")
    public String deletePainting(@PathVariable("name") String name) {
        paintingRepository.deleteByName(name);
        return "redirect:/all-painting-list";
    }

    //?
//    @RequestMapping("/addPaintingToUser")
//    public String addPaintingToUser(@ModelAttribute Painting painting, User user, Model model) {
//        model.addAttribute("painting", painting);
//        Set<Painting> paintings = user.getPaintings();
//        paintings.add(painting);
//        user.setPaintings(paintings);
//        userRepository.save(user);
//        return "redirect:/myList";
//    }

    @RequestMapping("/add-paintingToUser/{name}")
    public String addPaintingToUser(@PathVariable("name") Long id, String name) {
        final Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            final Painting painting = paintingRepository.findByName(name);
            user.get().getPaintings().add(painting);
            userService.updateUser(user.get());
            return "redirect:/myList";
        }
        return ("USER not found for this id : " + id);
    }

    @RequestMapping("/remove-painting/{name}")
    public String removePaintingToUser(@PathVariable("name") Long id, String name) throws UserNotFound {
        final Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            final Painting painting = paintingRepository.findByName(name);
            user.get().getPaintings().remove(painting);
            userService.updateUser(user.get());
            return "redirect:/myList";
        }else throw new UserNotFound();
    }

    @GetMapping("/myList")
    public String getUserPainting(Model model, Long id){
        model.addAttribute("paintingSet", paintingRepository.findByUserId(id));
        return "/myList";
    }
}