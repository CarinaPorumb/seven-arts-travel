package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Literature;
import ro.itschool.exception.LiteratureNotFound;
import ro.itschool.exception.UserNotFound;
import ro.itschool.repository.LiteratureRepository;
import ro.itschool.service.LiteratureService;

import java.util.List;
import java.util.Optional;

@Service
public class LiteratureServiceImpl implements LiteratureService {

    @Autowired
    LiteratureRepository literatureRepository;

    @Override
    public void deleteByName(String name) throws LiteratureNotFound {
        Optional.ofNullable(literatureRepository.findByName(name)).orElseThrow(LiteratureNotFound::new);
        literatureRepository.deleteByName(name);
    }


    @Override
    public void save(Literature literature) {
        literatureRepository.save(literature);
    }

    @Override
    public List<Literature> getAllLiteratures() {
        return literatureRepository.findAll();
    }

    @Override
    public Literature findByName(String name) throws LiteratureNotFound {
        return Optional.ofNullable(literatureRepository.findByName(name)).orElseThrow(LiteratureNotFound::new);
    }

    @Override
    public List<Literature> getAllLiteraturesByUserId(Integer userId) throws UserNotFound {
        return Optional.ofNullable(literatureRepository.findByUserId(userId)).orElseThrow(UserNotFound::new);
    }
}
