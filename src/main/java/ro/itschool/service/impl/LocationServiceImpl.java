package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Location;
import ro.itschool.exception.LocationNotFound;
import ro.itschool.exception.UserNotFound;
import ro.itschool.repository.LocationRepository;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.LocationService;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void deleteByName(String name) throws LocationNotFound {
        Optional.ofNullable(locationRepository.findByName(name)).orElseThrow(LocationNotFound::new);
        locationRepository.deleteByName(name);
    }

    @Override
    public void save(Location location) {
        locationRepository.save(location);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location findByName(String name) throws LocationNotFound {
        return Optional.ofNullable(locationRepository.findByName(name)).orElseThrow(LocationNotFound::new);

    }

    @Override
    public List<Location> getAllLocationByUserId(Integer userId) throws UserNotFound {
        final Optional<List<Location>> location = Optional.ofNullable(locationRepository.findByUserId(userId));
        if (location.isPresent())
            return locationRepository.findByUserId(userId);
        else throw new UserNotFound("UserId not found!");
    }

}


