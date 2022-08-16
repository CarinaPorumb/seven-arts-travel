package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Music;
import ro.itschool.exception.MusicNotFound;
import ro.itschool.exception.UserNotFound;
import ro.itschool.repository.MusicRepository;
import ro.itschool.service.MusicService;

import java.util.List;
import java.util.Optional;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    MusicRepository musicRepository;


    @Override
    public void deleteByName(String name) throws MusicNotFound {
        Optional.ofNullable(musicRepository.findByName(name)).orElseThrow(MusicNotFound::new);
        musicRepository.deleteByName(name);
    }

    @Override
    public void save(Music music) {
        musicRepository.save(music);
    }

    @Override
    public List<Music> getAllMusics() {
        return musicRepository.findAll();
    }

    @Override
    public Music findByName(String name) throws MusicNotFound {
        return Optional.ofNullable(musicRepository.findByName(name)).orElseThrow(MusicNotFound::new);
    }

    @Override
    public List<Music> getAllMusicsByUserId(Integer userId) throws UserNotFound {
        return Optional.ofNullable(musicRepository.findByUserId(userId)).orElseThrow(UserNotFound::new);
    }
}
