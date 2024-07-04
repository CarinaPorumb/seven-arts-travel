package ro.project.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ro.project.enums.Category;
import ro.project.enums.Status;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name = "art_event")
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ArtEvent extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "artevent_id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    private String imageLink;

    private String description;

    private String location;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean isTemporary;

    @ManyToMany
    @JoinTable(
            name = "art_event_movement",
            joinColumns = @JoinColumn(name = "art_event_id"),
            inverseJoinColumns = @JoinColumn(name = "movement_id")
    )
    @ToString.Exclude
    private Set<Movement> movements = new HashSet<>();

    @ManyToMany(mappedBy = "artEvents")
    @ToString.Exclude
    private Set<Artist> artists = new HashSet<>();

    @ManyToMany(mappedBy = "favoritesArtEvent")
    @ToString.Exclude
    private Set<User> favoritedBy = new HashSet<>();

    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtEvent artEvent = (ArtEvent) o;
        return Objects.equals(id, artEvent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}