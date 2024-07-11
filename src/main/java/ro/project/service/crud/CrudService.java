package ro.project.service.crud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CrudService<D, ID> {

    Page<D> getAll(Pageable pageable);

    Optional<D> getById(ID id);

    D save(D d);

    Optional<D> update(ID id, D d);

    boolean delete(ID id);

    Optional<D> patch(ID id, D d);
}