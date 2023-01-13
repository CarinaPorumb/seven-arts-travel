package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Painting;
import ro.itschool.exception.PaintingNotFound;

import java.util.List;

@Service
public interface PaintingService {

    void deleteByName(String name) throws PaintingNotFound;

    void save(Painting painting);

    List<Painting> getAllPaintings() throws PaintingNotFound;

    Painting findByName(String name) throws PaintingNotFound;

    List<Painting> getAllPaintingsByUserId(Long userId) throws PaintingNotFound;
}