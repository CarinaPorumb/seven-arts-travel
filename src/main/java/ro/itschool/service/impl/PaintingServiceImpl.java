package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Painting;
import ro.itschool.exception.PaintingNotFound;
import ro.itschool.repository.PaintingRepository;
import ro.itschool.service.PaintingService;

import java.util.List;
import java.util.Optional;

@Service
public class PaintingServiceImpl implements PaintingService {

    @Autowired
    PaintingRepository paintingRepository;

    @Override
    public void deleteByName(String name) throws PaintingNotFound {
        Optional.ofNullable(paintingRepository.findByName(name)).orElseThrow(PaintingNotFound::new);
        paintingRepository.deleteByName(name);
    }

    @Override
    public void save(Painting painting) throws PaintingNotFound {
        Optional.of(paintingRepository.save(painting)).orElseThrow(PaintingNotFound::new);
    }

    @Override
    public List<Painting> getAll() throws PaintingNotFound {
        return Optional.of(paintingRepository.findAll()).orElseThrow(PaintingNotFound::new);
    }

    @Override
    public Painting findByName(String name) throws PaintingNotFound {
        return Optional.ofNullable(paintingRepository.findByName(name)).orElseThrow(PaintingNotFound::new);
    }

    @Override
    public List<Painting> getAllByUserId(Long userId) throws PaintingNotFound {
        return Optional.ofNullable(paintingRepository.findByUserId(userId)).orElseThrow(PaintingNotFound::new);
    }

}