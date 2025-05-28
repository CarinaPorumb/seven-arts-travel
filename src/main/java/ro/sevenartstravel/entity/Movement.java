package ro.sevenartstravel.entity;

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
public class Movement extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "movement_id", unique = true, nullable = false)
    private UUID id;

    @NotBlank(message = "Name must not be blank")
    private String name; //    ANCIENT, RENAISSANCE, GOTHIC, BAROQUE, CLASSICISM, ROCOCO, NEOCLASSICISM, ROMANTICISM, REALISM, ARTNOUVEAU, IMPRESSIONISM,
    //POSTIMPRESSIONISM, SYMBOLISM, EXPRESSIONISM, CUBISM, SURREALISM, MODERNISM, CONTEMPORARY, AVANTGARDE, UNCERTAIN

    private String description;
    private Integer startYear;
    private Integer endYear;
    private String originRegion;

    @OneToMany(mappedBy = "movement")
    @ToString.Exclude
    @Builder.Default
    private Set<ArtObject> artObjects = new HashSet<>();

    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movement movement = (Movement) o;
        return Objects.equals(id, movement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}