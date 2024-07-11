package ro.project.service.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.project.enums.Category;
import ro.project.enums.Status;
import ro.project.model.ArtEventDTO;

import java.util.List;

public interface ArtEventService {

    Page<ArtEventDTO> getAllArtEvents(String name, String location, Category category, Status status, Integer pageNumber, Integer pageSize);

    List<ArtEventDTO> getByName(String name);

    List<ArtEventDTO> displayByCategory(Category category);

    Page<ArtEventDTO> search(String keyword, Pageable pageable);

}