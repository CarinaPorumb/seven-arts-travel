package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Architecture;
import ro.itschool.exception.ArchitectureNotFound;
import ro.itschool.exception.UserNotFound;
import java.util.List;

@Service
public interface ArchitectureService {

    void deleteByName(String name) throws ArchitectureNotFound;

    void save(Architecture architecture);

    List<Architecture> getAllArchitectures();

    Architecture findByName(String name) throws ArchitectureNotFound;

    List<Architecture> getAllArchitecturesByUserId(Integer userId) throws UserNotFound;

}
