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
    public void deleteByName(String name) {
        Painting painting = paintingRepository.findByName(name);
        paintingRepository.deleteByName(name);
    }

    @Override
    public void save(Painting painting) {
        paintingRepository.save(painting);
    }

    @Override
    public List<Painting> getAllPaintings() throws PaintingNotFound {
     return Optional.of(paintingRepository.findAll()).orElseThrow(PaintingNotFound::new);
    }

    @Override
    public Painting findByName(String name) {
     return paintingRepository.findByName(name);
    }

    @Override
    public List<Painting> getAllPaintingsByUserId(Integer userId) throws PaintingNotFound {
        return Optional.ofNullable(paintingRepository.findByUserId(userId)).orElseThrow(PaintingNotFound::new);
    }
}
