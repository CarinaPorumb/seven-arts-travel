package ro.itschool.service;

import ro.itschool.entity.ArtEvent;

import java.util.List;

public interface ArtEventService extends CrudService<ArtEvent, Integer> {

    List<ArtEvent> searchArtEvent(String keyword) throws Exception;

    List<ArtEvent> displayMusic(String keyword) throws Exception;

    List<ArtEvent> displayCinema(String keyword) throws Exception;

    List<ArtEvent> displayBalletAndTheatre(String keyword) throws Exception;

}