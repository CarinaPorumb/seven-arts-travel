package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Music;
import ro.itschool.entity.User;
import ro.itschool.exception.MusicNotFound;
import ro.itschool.repository.MusicRepository;
import ro.itschool.service.MusicService;

import java.util.Collections;
import java.util.Optional;

@Controller
public class MusicController {

    @Autowired
    MusicRepository musicRepository;

    @Autowired
    MusicService musicService;

    @GetMapping("/music")
    public String getMusicList(Model model, String keyword) {
        model.addAttribute("musicList", musicRepository.searchMusic(keyword));
        return "/music";
    }

    @GetMapping("/musicByName{name}")
    public String getMusicByName(Model model, String name) throws MusicNotFound {
        Optional.ofNullable(musicRepository.findByName(name)).orElseThrow(MusicNotFound::new);
        return "/music";
    }

    @GetMapping("/saveMusic")
    public String saveMusic1(Model model) {
        model.addAttribute("musicObject", new Music());
        return "saveMusic";
    }

    @PostMapping("/saveMusic")
    public String saveMusic2(@ModelAttribute Music music, Model model) {
        model.addAttribute("musicObject", music);
        musicRepository.save(music);
        return "redirect:/music";
    }

    @GetMapping("/updateMusic/{name}")
    public String updateMusic(@PathVariable String name) throws MusicNotFound {
        Optional.ofNullable(musicRepository.findByName(name)).orElseThrow(MusicNotFound::new);
        return "updateMusic";
    }

    @DeleteMapping("/deleteMusic/{name}")
    public String deleteMusic(@PathVariable String name) throws MusicNotFound {
        Optional.ofNullable(musicRepository.findByName(name)).orElseThrow(MusicNotFound::new);
        musicRepository.deleteByName(name);
        return "redirect:/music";
    }

    //?
    @PostMapping("/addMusicToUser")
    public String addMusicToUser(@ModelAttribute Music music, User user, Model model) {
        model.addAttribute("musicObject", music);
        user.setMusicSet(Collections.singleton(music));
        musicRepository.save(music);
        return "redirect:/music";
    }

}

