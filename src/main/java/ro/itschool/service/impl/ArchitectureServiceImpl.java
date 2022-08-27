package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Architecture;
import ro.itschool.exception.ArchitectureNotFound;
import ro.itschool.repository.ArchitectureRepository;
import ro.itschool.service.ArchitectureService;

import java.util.List;
import java.util.Optional;

@Service
public class ArchitectureServiceImpl implements ArchitectureService {

    @Autowired
    private ArchitectureRepository architectureRepository;


    public void deleteByName(String name) {
        Architecture architecture = architectureRepository.findByName(name);
        architectureRepository.deleteByName(name);
    }

    @Override
    public void save(Architecture architecture) {
        architectureRepository.save(architecture);
    }

    @Override
    public List<Architecture> getAllArchitectures() throws ArchitectureNotFound {
        return Optional.of(architectureRepository.findAll()).orElseThrow(ArchitectureNotFound::new);
    }

    @Override
    public Architecture findByName(String name)  {
        return architectureRepository.findByName(name);
    }

    @Override
    public List<Architecture> getAllArchitecturesByUserId(Integer userId) throws ArchitectureNotFound {
        return Optional.ofNullable(architectureRepository.findByUserId(userId)).orElseThrow(ArchitectureNotFound::new);
    }
}
