package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.ArtWork;
import ro.project.model.ArtWorkDTO;

@Mapper
public interface ArtWorkMapper {

    ArtWork dtoToArtWork(ArtWorkDTO dto);

    ArtWorkDTO artWorkToDto(ArtWork artWork);

}