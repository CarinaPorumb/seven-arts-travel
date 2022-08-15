package ro.itschool.service;

import ro.itschool.entity.Location;
import ro.itschool.exception.LocationNotFound;
import ro.itschool.exception.UserNotFound;

import java.util.List;

public interface LocationService {

    void deleteByName(String name) throws LocationNotFound;

    void save(Location location);

    List<Location> getAllLocations();

    Location findByName(String name) throws LocationNotFound;

    List<Location> getAllLocationsByUserId(Integer userId) throws UserNotFound;

}
