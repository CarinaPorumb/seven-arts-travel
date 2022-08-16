package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Painting;
import ro.itschool.exception.PaintingNotFound;
import ro.itschool.exception.UserNotFound;
import java.util.List;

@Service
public interface PaintingService {

    void deleteByName(String name) throws PaintingNotFound;

    void save(Painting painting);

    List<Painting> getAllPaintings();

    Painting findByName(String name) throws PaintingNotFound;

    List<Painting> getAllPaintingsByUserId(Integer userId) throws UserNotFound;
}
