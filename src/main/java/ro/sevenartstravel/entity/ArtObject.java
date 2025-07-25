package ro.sevenartstravel.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ro.sevenartstravel.enums.ArtCategory;
import ro.sevenartstravel.enums.ArtObjectType;
import ro.sevenartstravel.enums.Status;

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
@Table(name = "art_object")
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ArtObject extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "artobject_id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Name must not be blank")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String shortDescription;
    private String longDescription;
    private Integer year;  // for artWork
    private String location;
    private String imageUrl;
    private String externalUrl;
    private LocalDateTime startTime; //for events
    private LocalDateTime endTime; //for events
    private Boolean isTemporary;   //for events

    @Enumerated(EnumType.STRING)
    private Status status; // for events

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArtCategory artCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArtObjectType artObjectType;

    @ManyToOne
    @JoinColumn(name = "movement_id")
    private Movement movement;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "artist_art_object",
            joinColumns = @JoinColumn(name = "art_object_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    @ToString.Exclude
    @Builder.Default
    private Set<Artist> artists = new HashSet<>();

    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtObject artObject = (ArtObject) o;
        return Objects.equals(id, artObject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}