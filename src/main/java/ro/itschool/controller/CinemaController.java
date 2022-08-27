package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Cinema;
import ro.itschool.entity.User;
import ro.itschool.exception.CinemaNotFound;
import ro.itschool.repository.CinemaRepository;
import ro.itschool.service.CinemaService;

import java.util.Collections;
import java.util.Optional;

@Controller
public class CinemaController {

    @Autowired
    CinemaRepository cinemaRepository;
    @Autowired
    CinemaService cinemaService;

    @GetMapping("/cinema")
    public String getCinemas(Model model, String keyword) {
        model.addAttribute("cinemas", cinemaRepository.searchCinema(keyword));
        return "/cinema";
    }

    @GetMapping("/saveCinema")
    public String saveCinema1(Model model) {
        model.addAttribute("cinemaObject", new Cinema());
        return "saveCinema";
    }

    @PostMapping("/saveCinema")
    public String saveCinema2(@ModelAttribute Cinema cinema, Model model) {
        model.addAttribute("cinemaObject", cinema);
        cinemaRepository.save(cinema);
        return "redirect:/cinema";
    }

    @GetMapping("/updateCinema/{name}")
    public String updateCinema(@PathVariable String name) throws CinemaNotFound {
        Optional.ofNullable(cinemaRepository.findByName(name)).orElseThrow(CinemaNotFound::new);
        return "updateCinema";
    }

    @RequestMapping(path = "/cinema/delete/{name}")
    public String deleteCinema(@PathVariable("name") String name) {
        cinemaRepository.deleteByName(name);
        return "redirect:/cinema";
    }


    //?
    @PostMapping("/addCinemaToUser")
    public String addCinemaToUser(@ModelAttribute Cinema cinema, User user, Model model) {
        model.addAttribute("cinemaObject", cinema);
        user.setCinemas(Collections.singleton(cinema));
        cinemaRepository.save(cinema);
        return "redirect:/cinema";
    }
}
