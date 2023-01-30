package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.ArtObject;
import ro.itschool.exception.ArtObjectNotFound;
import ro.itschool.repository.ArtObjectRepository;
import ro.itschool.service.ArtObjectService;

import java.util.List;
import java.util.Optional;
@Service
public class ArtObjectServiceImpl implements ArtObjectService {
    @Autowired
    private ArtObjectRepository artObjectRepository;

    @Override
    public void deleteByName(String name) throws Exception {
        Optional.ofNullable(artObjectRepository.findByName(name)).orElseThrow(ArtObjectNotFound::new);
        artObjectRepository.deleteByName(name);
    }

    @Override
    public void save(ArtObject artObject) throws Exception {
        Optional.of(artObjectRepository.save(artObject)).orElseThrow(ArtObjectNotFound::new);
    }

    @Override
    public List<ArtObject> getAll() throws Exception {
        return Optional.of(artObjectRepository.findAll()).orElseThrow(ArtObjectNotFound::new);
    }

    @Override
    public ArtObject findByName(String name) throws Exception {
        return Optional.ofNullable(artObjectRepository.findByName(name)).orElseThrow(ArtObjectNotFound::new);
    }

    @Override
    public List<ArtObject> getAllByUserId(Long userId) throws Exception {
        return Optional.ofNullable(artObjectRepository.findByUserId(userId)).orElseThrow(ArtObjectNotFound::new);
    }
}