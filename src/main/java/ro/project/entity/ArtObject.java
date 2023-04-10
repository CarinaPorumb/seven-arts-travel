package ro.project.entity;

import jakarta.persistence.*;
import lombok.*;
import ro.project.enums.Category;
import ro.project.enums.Style;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class ArtObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String imageLink;
    private String author;
    private Integer year;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Style movement;
    private Boolean isTemporary;
    private String location;

    @ManyToOne
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtObject artObject = (ArtObject) o;
        return Objects.equals(id, artObject.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}