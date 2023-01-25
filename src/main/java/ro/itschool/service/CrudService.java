package ro.itschool.service;

import java.util.List;

public interface CrudService<A, ID> {

    void deleteByName(String name) throws Exception;

    void save(A a) throws Exception;

    List<A> getAll() throws Exception;

    A findByName(String name) throws Exception;

    List<A> getAllByUserId(Long userId) throws Exception;
}