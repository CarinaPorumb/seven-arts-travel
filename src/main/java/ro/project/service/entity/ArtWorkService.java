package ro.project.service.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.project.enums.Category;
import ro.project.model.ArtWorkDTO;

import java.util.List;

public interface ArtWorkService {

    Page<ArtWorkDTO> getAllArtWorks(String name, Integer year, String location, Category category, Integer pageNumber, Integer pageSize);

    List<ArtWorkDTO> getByName(String name);

    List<ArtWorkDTO> displayByCategory(Category category);

    Page<ArtWorkDTO> search(String keyword, Pageable pageable);

}