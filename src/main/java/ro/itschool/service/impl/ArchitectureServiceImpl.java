package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Architecture;
import ro.itschool.exception.ArchitectureNotFound;
import ro.itschool.exception.UserNotFound;
import ro.itschool.repository.ArchitectureRepository;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.ArchitectureService;

import java.util.List;
import java.util.Optional;

@Service
public class ArchitectureServiceImpl implements ArchitectureService {

    @Autowired
    private ArchitectureRepository architectureRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void deleteByName(String name) throws ArchitectureNotFound {
        Optional.ofNullable(architectureRepository.findByName(name)).orElseThrow(ArchitectureNotFound::new);
        architectureRepository.deleteByName(name);
    }

    @Override
    public void save(Architecture architecture) {
        architectureRepository.save(architecture);
    }

    @Override
    public List<Architecture> getAllArchitectures() {
        return null;
    }

    @Override
    public Architecture findByName(String name) throws ArchitectureNotFound {
        return null;
    }

    @Override
    public List<Architecture> getAllArchitecturesByUserId(Integer userId) throws UserNotFound {
        return null;
    }
}
