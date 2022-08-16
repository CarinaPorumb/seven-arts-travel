package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.BalletAndTheatre;
import ro.itschool.exception.BalletAndTheatreNotFound;
import ro.itschool.exception.UserNotFound;

import java.util.List;

@Service
public interface BalletAndTheatreService {

    void deleteByName(String name) throws BalletAndTheatreNotFound;

    void save(BalletAndTheatre balletAndTheatre);

    List<BalletAndTheatre> getAllBalletsAndTheatres();

    BalletAndTheatre findByName(String name) throws BalletAndTheatreNotFound;

    List<BalletAndTheatre> getAllBalletsAndTheatresByUserId(Integer userId) throws UserNotFound;
}
