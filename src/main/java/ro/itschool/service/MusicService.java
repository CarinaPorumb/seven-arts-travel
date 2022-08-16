package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Music;
import ro.itschool.exception.MusicNotFound;
import ro.itschool.exception.UserNotFound;

import java.util.List;

@Service
public interface MusicService {

    void deleteByName(String name) throws MusicNotFound;

    void save(Music music);

    List<Music> getAllMusics();

    Music findByName(String name) throws MusicNotFound;

    List<Music> getAllMusicsByUserId(Integer userId) throws UserNotFound;

}
