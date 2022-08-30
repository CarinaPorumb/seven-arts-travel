package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Music;
import ro.itschool.entity.User;
import ro.itschool.exception.MusicNotFound;
import ro.itschool.repository.MusicRepository;
import ro.itschool.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@Controller
public class MusicController {

    @Autowired
    MusicRepository musicRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all-music-list")
    public String getMusic(Model model, String keyword) {
        model.addAttribute("musics", musicRepository.searchMusic(keyword));
        return "/music";
    }

    @GetMapping("/save-music")
    public String saveMusic1(Model model) {
        model.addAttribute("musicObject", new Music());
        return "save-music";
    }

    @PostMapping("/save-music")
    public String saveMusic2(@ModelAttribute Music music, Model model) {
        model.addAttribute("musicObject", music);
        musicRepository.save(music);
        return "redirect:/all-music-list";
    }

    @GetMapping(path = "/update-music/{name}")
    public String updateMusic(@PathVariable("name") String name, Model model) throws MusicNotFound {
        Music music = Optional.ofNullable(musicRepository.findByName(name)).orElseThrow(MusicNotFound::new);
        model.addAttribute("music", music);
        return "update-music";
    }

    @RequestMapping(path = "/delete-music/{name}")
    public String deleteMusic(@PathVariable("name") String name) {
        musicRepository.deleteByName(name);
        return "redirect:/all-music-list";
    }

    //?
    @RequestMapping("/addMusicToUser")
    public String addMusicToUser(@ModelAttribute Music music, User user, Model model) {
        model.addAttribute("musicObject", music);
        Set<Music> music1 = user.getMusicSet();
        music1.add(music);
        user.setMusicSet(music1);
        userRepository.save(user);
        return "redirect:/myList";
    }


}

