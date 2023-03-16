package ro.itschool.service;

import ro.itschool.entity.ArtObject;

import java.util.List;

public interface ArtObjectService extends CrudService<ArtObject, Integer> {

    List<ArtObject> searchArtObject(String keyword) throws Exception;

    List<ArtObject> displayArchitecture(String keyword) throws Exception;

    List<ArtObject> displaySculpture(String keyword) throws Exception;

    List<ArtObject> displayLiterature(String keyword) throws Exception;

    List<ArtObject> displayPainting(String keyword) throws Exception;

}