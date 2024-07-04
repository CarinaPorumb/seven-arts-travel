package ro.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Artist extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "artist_id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    private String biography;
    private String imageLink;
    private Integer birthYear;
    private Integer deathYear;
    private String nationality;

    public Artist(String name, String biography, String imageLink, Integer birthYear, Integer deathYear, String nationality) {
        this.name = name;
        this.biography = biography;
        this.imageLink = imageLink;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.nationality = nationality;
    }

    @ManyToMany
    @JoinTable(
            name = "artist_art_work",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "art_work_id")
    )
    @ToString.Exclude
    private Set<ArtWork> artWorks = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "artist_art_event",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "art_event_id")
    )
    @ToString.Exclude
    private Set<ArtEvent> artEvents = new HashSet<>();

    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;
        return Objects.equals(id, artist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}