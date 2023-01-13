package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Cinema;
import ro.itschool.exception.CinemaNotFound;
import ro.itschool.repository.CinemaRepository;
import ro.itschool.service.CinemaService;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    CinemaRepository cinemaRepository;

    @Override
    public void deleteByName(String name) {
        Cinema cinema = cinemaRepository.findByName(name);
        cinemaRepository.deleteByName(name);
    }

    @Override
    public void save(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    @Override
    public List<Cinema> getAllCinemas() throws CinemaNotFound {
        return Optional.of(cinemaRepository.findAll()).orElseThrow(CinemaNotFound::new);
    }

    @Override
    public Cinema findByName(String name)  {
        return cinemaRepository.findByName(name);
    }

    @Override
    public List<Cinema> getAllCinemasByUserId(Integer userId) throws CinemaNotFound {
        return Optional.ofNullable(cinemaRepository.findByUserId(userId)).orElseThrow(CinemaNotFound::new);
    }
}