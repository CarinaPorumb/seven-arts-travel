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
    public void deleteByName(String name) {
        Sculpture sculpture = sculptureRepository.findByName(name);
        sculptureRepository.deleteByName(name);
    }

    @Override
    public void save(Sculpture sculpture) {
        sculptureRepository.save(sculpture);
    }

    @Override
    public List<Sculpture> getAllSculptures() throws SculptureNotFound {
        return Optional.of(sculptureRepository.findAll()).orElseThrow(SculptureNotFound::new);
    }

    @Override
    public Sculpture findByName(String name) {
        return sculptureRepository.findByName(name);
    }

    @Override
    public List<Sculpture> getAllSculpturesByUserId(Integer userId) throws SculptureNotFound {
        return Optional.ofNullable(sculptureRepository.findByUserId(userId)).orElseThrow(SculptureNotFound::new);
    }
}