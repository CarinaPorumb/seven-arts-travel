package ro.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Artist implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String biography;
    private String imageLink;
    private Integer birthYear;
    private Integer deathYear;
    private String nationality;

    @ManyToMany
    @JoinTable(
            name = "artist_art_work",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "art_work_id")
    )
    private Set<ArtWork> artWorks;


    @ManyToMany
    @JoinTable(
            name = "artist_art_event",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "art_event_id")
    )
    private Set<ArtEvent> artEvents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        return id.equals(artist.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}