package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.BalletAndTheatre;
import ro.itschool.exception.BalletAndTheatreNotFound;
import ro.itschool.repository.BalletAndTheatreRepository;
import ro.itschool.service.BalletAndTheatreService;

import java.util.List;
import java.util.Optional;

@Service
public class BalletAndTheatreServiceImpl implements BalletAndTheatreService {

    @Autowired
    BalletAndTheatreRepository balletAndTheatreRepository;

    @Override
    public void deleteByName(String name)  {
        BalletAndTheatre balletAndTheatre = balletAndTheatreRepository.findByName(name);
        balletAndTheatreRepository.deleteByName(name);
    }

    @Override
    public void save(BalletAndTheatre balletAndTheatre) {
        balletAndTheatreRepository.save(balletAndTheatre);
    }

    @Override
    public List<BalletAndTheatre> getAllBalletsAndTheatres() throws BalletAndTheatreNotFound {
        return Optional.of(balletAndTheatreRepository.findAll()).orElseThrow(BalletAndTheatreNotFound::new);
    }

    @Override
    public BalletAndTheatre findByName(String name) {
        return balletAndTheatreRepository.findByName(name);
    }

    @Override
    public List<BalletAndTheatre> getAllBalletsAndTheatresByUserId(Integer userId) throws BalletAndTheatreNotFound {
        return Optional.ofNullable(balletAndTheatreRepository.findByUserId(userId)).orElseThrow(BalletAndTheatreNotFound::new);
    }
}
