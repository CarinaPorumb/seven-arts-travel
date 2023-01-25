package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Literature;
import ro.itschool.exception.LiteratureNotFound;
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
    public void save(Literature literature) throws LiteratureNotFound {
        Optional.of(literatureRepository.save(literature)).orElseThrow(LiteratureNotFound::new);
    }

    @Override
    public List<Literature> getAll() throws LiteratureNotFound {
        return Optional.of(literatureRepository.findAll()).orElseThrow(LiteratureNotFound::new);
    }

    @Override
    public Literature findByName(String name) throws LiteratureNotFound {
        return Optional.ofNullable(literatureRepository.findByName(name)).orElseThrow(LiteratureNotFound::new);
    }

    @Override
    public List<Literature> getAllByUserId(Long userId) throws LiteratureNotFound {
        return Optional.ofNullable(literatureRepository.findByUserId(userId)).orElseThrow(LiteratureNotFound::new);
    }

}