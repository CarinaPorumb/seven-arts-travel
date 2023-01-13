package ro.itschool.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String fullName;
    private String email;

    private List<String> roles = new ArrayList<>();
    private List<ArchitectureDTO> architectureDTOList = new ArrayList<>();
    private List<BalletAndTheatreDTO> balletAndTheatreDTOSList = new ArrayList<>();
    private List<CinemaDTO> cinemaDTOList = new ArrayList<>();
    private List<LiteratureDTO> literatureDTOList = new ArrayList<>();
    private List<MusicDTO> musicDTOList = new ArrayList<>();
    private List<PaintingDTO> paintingDTOList = new ArrayList<>();
    private List<SculptureDTO> sculptureDTOList = new ArrayList<>();

}