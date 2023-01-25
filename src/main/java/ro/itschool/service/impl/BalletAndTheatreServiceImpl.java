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
    public void deleteByName(String name) throws BalletAndTheatreNotFound {
        Optional.ofNullable(balletAndTheatreRepository.findByName(name)).orElseThrow(BalletAndTheatreNotFound::new);
        balletAndTheatreRepository.deleteByName(name);
    }

    @Override
    public void save(BalletAndTheatre balletAndTheatre) throws BalletAndTheatreNotFound {
        Optional.of(balletAndTheatreRepository.save(balletAndTheatre)).orElseThrow(BalletAndTheatreNotFound::new);
    }

    @Override
    public List<BalletAndTheatre> getAll() throws BalletAndTheatreNotFound {
        return Optional.of(balletAndTheatreRepository.findAll()).orElseThrow(BalletAndTheatreNotFound::new);
    }

    @Override
    public BalletAndTheatre findByName(String name) throws BalletAndTheatreNotFound {
        return Optional.ofNullable(balletAndTheatreRepository.findByName(name)).orElseThrow(BalletAndTheatreNotFound::new);
    }

    @Override
    public List<BalletAndTheatre> getAllByUserId(Long userId) throws BalletAndTheatreNotFound {
        return Optional.ofNullable(balletAndTheatreRepository.findByUserId(userId)).orElseThrow(BalletAndTheatreNotFound::new);
    }
}