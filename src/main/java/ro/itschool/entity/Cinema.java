package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.*;
import ro.itschool.enums.Style;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Cinema implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue
    private UUID id = UUID.randomUUID();

    private String name;

    private String imageLink;

    @Enumerated(EnumType.STRING)
    private Style movement;

    private Boolean isTemporary;

    private LocalDate eventTime;

    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Style getMovement() {
        return movement;
    }

    public void setMovement(Style movement) {
        this.movement = movement;
    }

    public Boolean getTemporary() {
        return isTemporary;
    }

    public void setTemporary(Boolean temporary) {
        isTemporary = temporary;
    }

    public LocalDate getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDate eventTime) {
        this.eventTime = eventTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
