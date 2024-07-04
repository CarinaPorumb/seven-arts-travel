package ro.project.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ro.project.enums.Category;

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
@Table(name = "art_work")
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ArtWork extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "artwork_id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    private String imageLink;
    private String description;
    private Integer year;
    private String location;
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "art_work_movement",
            joinColumns = @JoinColumn(name = "art_work_id"),
            inverseJoinColumns = @JoinColumn(name = "movement_id")
    )
    @ToString.Exclude
    private Set<Movement> movements = new HashSet<>();

    @ManyToMany(mappedBy = "artWorks")
    @ToString.Exclude
    private Set<Artist> artists = new HashSet<>();

    @ManyToMany(mappedBy = "favoritesArtWork")
    @ToString.Exclude
    private Set<User> favoritedBy = new HashSet<>();

    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtWork artWork = (ArtWork) o;
        return Objects.equals(id, artWork.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}