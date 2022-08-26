package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.itschool.repository.ArchitectureRepository;
import ro.itschool.repository.MusicRepository;

@Controller
public class ContentController {

    @Autowired
    MusicRepository musicRepository;

    @Autowired
    ArchitectureRepository architectureRepository;

    @GetMapping("/content")
    public String getMusicList(Model model, String keyword) {
        model.addAttribute("musicList", musicRepository.searchMusic(keyword));
        model.addAttribute("architectures", architectureRepository.searchArchitecture(keyword));
        return "content";
    }
}
