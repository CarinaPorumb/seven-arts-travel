package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Sculpture;
import ro.itschool.exception.SculptureNotFound;
import ro.itschool.exception.UserNotFound;

import java.util.List;

@Service
public interface SculptureService {

    void deleteByName(String name) throws SculptureNotFound;

    void save(Sculpture sculpture);

    List<Sculpture> getAllSculptures();

    Sculpture findByName(String name) throws SculptureNotFound;

    List<Sculpture> getAllSculpturesByUserId(Integer userId) throws UserNotFound;
}
