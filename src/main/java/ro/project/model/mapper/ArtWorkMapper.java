package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.ArtWork;
import ro.project.model.ArtWorkDTO;

@Mapper(componentModel = "spring")
public interface ArtWorkMapper {

    ArtWork toEntity(ArtWorkDTO dto);

    ArtWorkDTO toDTO(ArtWork artWork);

}