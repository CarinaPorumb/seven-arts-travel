package ro.itschool.service;

import ro.itschool.entity.ArtEvent;

import java.util.List;

public interface CrudService<A, ID> {

    void deleteByName(String name) throws Exception;

    ArtEvent save(A a) throws Exception;

    List<A> getAll() throws Exception;

    A findByName(String name) throws Exception;

    List<A> getAllByUserId(Long userId) throws Exception;
}