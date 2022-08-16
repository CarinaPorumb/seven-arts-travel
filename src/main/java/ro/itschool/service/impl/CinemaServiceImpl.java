package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Cinema;
import ro.itschool.exception.CinemaNotFound;
import ro.itschool.exception.UserNotFound;
import ro.itschool.repository.CinemaRepository;
import ro.itschool.service.CinemaService;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    CinemaRepository cinemaRepository;

    @Override
    public void deleteByName(String name) throws CinemaNotFound {
        Optional.ofNullable(cinemaRepository.findByName(name)).orElseThrow(CinemaNotFound::new);
        cinemaRepository.deleteByName(name);
    }

    @Override
    public void save(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    @Override
    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    @Override
    public Cinema findByName(String name) throws CinemaNotFound {
        return Optional.ofNullable(cinemaRepository.findByName(name)).orElseThrow(CinemaNotFound::new);
    }

    @Override
    public List<Cinema> getAllCinemasByUserId(Integer userId) throws UserNotFound {
        return Optional.ofNullable(cinemaRepository.findByUserId(userId)).orElseThrow(UserNotFound::new);
    }
}
