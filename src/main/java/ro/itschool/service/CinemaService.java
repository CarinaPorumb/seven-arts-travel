package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Cinema;
import ro.itschool.exception.CinemaNotFound;
import ro.itschool.exception.UserNotFound;

import java.util.List;

@Service
public interface CinemaService {

    void deleteByName(String name) throws CinemaNotFound;

    void save(Cinema cinema);

    List<Cinema> getAllCinemas();

    Cinema findByName(String name) throws CinemaNotFound;

    List<Cinema> getAllCinemasByUserId(Integer userId) throws UserNotFound;
}
