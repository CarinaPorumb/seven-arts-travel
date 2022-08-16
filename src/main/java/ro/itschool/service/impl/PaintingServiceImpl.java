package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Painting;
import ro.itschool.exception.PaintingNotFound;
import ro.itschool.exception.UserNotFound;
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
    public void save(Painting painting) {
        paintingRepository.save(painting);

    }

    @Override
    public List<Painting> getAllPaintings() {
        return paintingRepository.findAll();
    }

    @Override
    public Painting findByName(String name) throws PaintingNotFound {
        return Optional.ofNullable(paintingRepository.findByName(name)).orElseThrow(PaintingNotFound::new);

    }

    @Override
    public List<Painting> getAllPaintingsByUserId(Integer userId) throws UserNotFound {
        return Optional.ofNullable(paintingRepository.findByUserId(userId)).orElseThrow(UserNotFound::new);
    }
}
