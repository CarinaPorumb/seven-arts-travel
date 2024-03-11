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
public class Movement implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;   // ANCIENT, RENAISSANCE, GOTHIC, BAROQUE, CLASSICISM, ROCOCO, NEOCLASSICISM, ROMANTICISM,
    // REALISM, ARTNOUVEAU, IMPRESSIONISM, POSTIMPRESSIONISM, SYMBOLISM, EXPRESSIONISM, CUBISM, SURREALISM, MODERNISM,
    // CONTEMPORARY, AVANTGARDE, UNCERTAIN

    private String description;
    private Integer startYear;
    private Integer endYear;

    @ManyToMany(mappedBy = "movements")
    private Set<ArtWork> artWorks;

    @ManyToMany(mappedBy = "movements")
    private Set<ArtEvent> artEvents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movement movement = (Movement) o;

        return id.equals(movement.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}