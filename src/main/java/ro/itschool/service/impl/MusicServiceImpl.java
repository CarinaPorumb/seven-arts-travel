package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Music;
import ro.itschool.exception.MusicNotFound;
import ro.itschool.repository.MusicRepository;
import ro.itschool.service.MusicService;

import java.util.List;
import java.util.Optional;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    MusicRepository musicRepository;


    @Override
    public void deleteByName(String name) {
        Music music = musicRepository.findByName(name);
        musicRepository.deleteByName(name);
    }

    @Override
    public void save(Music music) {
        musicRepository.save(music);
    }

    @Override
    public List<Music> getAllMusics() throws MusicNotFound {
        return Optional.of(musicRepository.findAll()).orElseThrow(MusicNotFound::new);
    }

    @Override
    public Music findByName(String name) {
        return musicRepository.findByName(name);
    }

    @Override
    public List<Music> getAllMusicsByUserId(Integer userId) throws MusicNotFound {
        return Optional.ofNullable(musicRepository.findByUserId(userId)).orElseThrow(MusicNotFound::new);
    }
}
