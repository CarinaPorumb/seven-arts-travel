package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Music;
import ro.itschool.exception.MusicNotFound;

import java.util.List;

@Service
public interface MusicService {

    void deleteByName(String name);

    void save(Music music);

    List<Music> getAllMusics() throws MusicNotFound;

    Music findByName(String name);

    List<Music> getAllMusicsByUserId(Integer userId) throws MusicNotFound;
}