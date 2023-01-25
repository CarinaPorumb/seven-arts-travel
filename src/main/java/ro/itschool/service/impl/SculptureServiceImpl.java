package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Sculpture;
import ro.itschool.exception.SculptureNotFound;
import ro.itschool.repository.SculptureRepository;
import ro.itschool.service.SculptureService;

import java.util.List;
import java.util.Optional;

@Service
public class SculptureServiceImpl implements SculptureService {

    @Autowired
    private SculptureRepository sculptureRepository;

    @Override
    public void deleteByName(String name) throws SculptureNotFound {
        Optional.ofNullable(sculptureRepository.findByName(name)).orElseThrow(SculptureNotFound::new);
        sculptureRepository.deleteByName(name);
    }

    @Override
    public void save(Sculpture sculpture) throws SculptureNotFound {
        Optional.of(sculptureRepository.save(sculpture)).orElseThrow(SculptureNotFound::new);
    }

    @Override
    public List<Sculpture> getAll() throws SculptureNotFound {
        return Optional.of(sculptureRepository.findAll()).orElseThrow(SculptureNotFound::new);
    }

    @Override
    public Sculpture findByName(String name) throws SculptureNotFound {
        return Optional.ofNullable(sculptureRepository.findByName(name)).orElseThrow(SculptureNotFound::new);
    }

    @Override
    public List<Sculpture> getAllByUserId(Long userId) throws SculptureNotFound {
        return Optional.ofNullable(sculptureRepository.findByUserId(userId)).orElseThrow(SculptureNotFound::new);
    }

}