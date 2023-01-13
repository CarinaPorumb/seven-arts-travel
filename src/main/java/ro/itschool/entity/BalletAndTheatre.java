package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.*;
import ro.itschool.enums.Style;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class BalletAndTheatre implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue
    private UUID id = UUID.randomUUID();

    private String name;
    private String imageLink;
    private String author;
    @Enumerated(EnumType.STRING)
    private Style movement;
    private Boolean isTemporary;
    private LocalDateTime eventTime;
    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

}