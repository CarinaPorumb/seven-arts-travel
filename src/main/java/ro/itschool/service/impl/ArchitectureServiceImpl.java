package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Architecture;
import ro.itschool.exception.ArchitectureNotFound;
import ro.itschool.exception.UserNotFound;
import ro.itschool.repository.ArchitectureRepository;
import ro.itschool.service.ArchitectureService;

import java.util.List;

@Service
public class ArchitectureServiceImpl implements ArchitectureService {

    @Autowired
    private ArchitectureRepository architectureRepository;


    public void deleteByName(String name) throws ArchitectureNotFound {
        Architecture architecture = architectureRepository.findByName(name);
        architectureRepository.deleteByName(name);
    }
//     Optional.ofNullable(architectureRepository.findByName(name)).orElseThrow(ArchitectureNotFound::new);
//        architectureRepository.deleteByName(name);

    @Override
    public void save(Architecture architecture) {
        architectureRepository.save(architecture);
    }

    @Override
    public List<Architecture> getAllArchitectures() {
        return architectureRepository.findAll();
    }

    @Override
    public Architecture findByName(String name) throws ArchitectureNotFound {
        return architectureRepository.findByName(name);
    }

//    return Optional.ofNullable(architectureRepository.findByName(name)).orElseThrow(ArchitectureNotFound::new);

    @Override
    public List<Architecture> getAllArchitecturesByUserId(Integer userId) throws UserNotFound {
        return architectureRepository.findByUserId(userId);
    }

//      return Optional.ofNullable(architectureRepository.findByUserId(userId)).orElseThrow(UserNotFound::new);
}
