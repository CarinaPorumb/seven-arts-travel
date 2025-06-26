package ro.sevenartstravel.service.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.sevenartstravel.dto.ArtObjectDTO;
import ro.sevenartstravel.enums.ArtCategory;
import ro.sevenartstravel.enums.ArtObjectType;

public interface ArtObjectService {

    Page<ArtObjectDTO> getAll(String title, String shortDescription, String longDescription, String location, ArtCategory artCategory, Integer year, Pageable pageable);

    Page<ArtObjectDTO> getByTitle(String title, Pageable pageable);

    Page<ArtObjectDTO> getByYear(int year, Pageable pageable);

    Page<ArtObjectDTO> getByCategory(ArtCategory artCategory, Pageable pageable);

    Page<ArtObjectDTO> getByLocation(String location, Pageable pageable);

    Page<ArtObjectDTO> getByYearRange(int startYear, int endYear, Pageable pageable);

    Page<ArtObjectDTO> searchArtObject(String keyword, Pageable pageable);

    Page<ArtObjectDTO> getByArtObjectType(ArtObjectType artObjectType, Pageable pageable);

}