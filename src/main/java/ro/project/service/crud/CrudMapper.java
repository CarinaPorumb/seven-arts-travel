package ro.project.service.crud;

public interface CrudMapper<D, E> {

    E toEntity(D dto);

    D toDTO(E entity);

    void updateEntity(E entity, D dto);

    void patchEntity(E entity, D dto);

}