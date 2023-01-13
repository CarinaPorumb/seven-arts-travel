package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.BalletAndTheatre;
import ro.itschool.exception.BalletAndTheatreNotFound;
import ro.itschool.exception.UserNotFound;

import java.util.List;

@Service
public interface BalletAndTheatreService {

    void deleteByName(String name);

    void save(BalletAndTheatre balletAndTheatre);

    List<BalletAndTheatre> getAllBalletsAndTheatres() throws BalletAndTheatreNotFound;

    BalletAndTheatre findByName(String name);

    List<BalletAndTheatre> getAllBalletsAndTheatresByUserId(Integer userId) throws UserNotFound, BalletAndTheatreNotFound;
}