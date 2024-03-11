package ro.project.entity;

import jakarta.persistence.*;
import lombok.*;
import ro.project.enums.Category;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class ArtEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String imageLink;
    private String location;
    @Enumerated(EnumType.STRING)
    private Category category;
    private LocalDateTime eventTime;
    private Boolean isTemporary;

    @ManyToMany
    @JoinTable(
            name = "art_event_movement",
            joinColumns = @JoinColumn(name = "art_event_id"),
            inverseJoinColumns = @JoinColumn(name = "movement_id")
    )
    private Set<Movement> movements;

    @ManyToMany(mappedBy = "artEvents")
    private Set<Artist> artists;

    @ManyToMany(mappedBy = "favoritesArtEvent")
    private Set<User> favoritedBy = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtEvent artEvent = (ArtEvent) o;
        return Objects.equals(id, artEvent.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}