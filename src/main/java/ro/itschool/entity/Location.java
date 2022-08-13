package ro.itschool.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.itschool.enums.Category;
import ro.itschool.enums.Movement;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
public class Location implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue
    private UUID id = UUID.randomUUID();

    private String name;

    private Movement movement;

    private Category category;

    private Boolean isTemporary;

    private Integer year;

    private String country;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

}
