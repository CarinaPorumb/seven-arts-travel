package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Cinema;
import ro.itschool.entity.User;
import ro.itschool.exception.CinemaNotFound;
import ro.itschool.repository.CinemaRepository;
import ro.itschool.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@Controller
public class CinemaController {

    @Autowired
    CinemaRepository cinemaRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/all-cinema-list")
    public String getCinemas(Model model, String keyword) {
        model.addAttribute("cinemas", cinemaRepository.searchCinema(keyword));
        return "/cinema";
    }

    @GetMapping("/save-cinema")
    public String saveCinema1(Model model) {
        model.addAttribute("cinemaObject", new Cinema());
        return "save-cinema";
    }

    @PostMapping("/save-cinema")
    public String saveCinema2(@ModelAttribute Cinema cinema, Model model) {
        model.addAttribute("cinemaObject", cinema);
        cinemaRepository.save(cinema);
        return "redirect:/all-cinema-list";
    }

    @GetMapping(path = "/update-cinema/{name}")
    public String updateCinema(@PathVariable("name") String name, Model model) throws CinemaNotFound {
        Cinema cinema = Optional.ofNullable(cinemaRepository.findByName(name)).orElseThrow(CinemaNotFound::new);
        model.addAttribute("cinema", cinema);
        return "update-cinema";
    }

    @RequestMapping(path = "/delete-cinema/{name}")
    public String deleteCinema(@PathVariable("name") String name) {
        cinemaRepository.deleteByName(name);
        return "redirect:/all-cinema-list";
    }

    //?
    @RequestMapping("/addCinemaToUser")
    public String addCinemaToUser(@ModelAttribute Cinema cinema, User user, Model model) {
        model.addAttribute("cinema", cinema);
        Set<Cinema> cinemas = user.getCinemas();
        cinemas.add(cinema);
        user.setCinemas(cinemas);
        userRepository.save(user);
        return "redirect:/myList";
    }
}
