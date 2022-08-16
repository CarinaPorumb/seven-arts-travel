package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Literature;
import ro.itschool.exception.LiteratureNotFound;
import ro.itschool.exception.UserNotFound;

import java.util.List;

@Service
public interface LiteratureService {

    void deleteByName(String name) throws LiteratureNotFound;

    void save(Literature literature);

    List<Literature> getAllLiteratures();

    Literature findByName(String name) throws LiteratureNotFound;

    List<Literature> getAllLiteraturesByUserId(Integer userId) throws UserNotFound;
}
