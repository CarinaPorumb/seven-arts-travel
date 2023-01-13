package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Sculpture;
import ro.itschool.exception.SculptureNotFound;

import java.util.List;

@Service
public interface SculptureService {

    void deleteByName(String name) ;

    void save(Sculpture sculpture);

    List<Sculpture> getAllSculptures() throws SculptureNotFound;

    Sculpture findByName(String name) ;

    List<Sculpture> getAllSculpturesByUserId(Integer userId) throws SculptureNotFound;
}