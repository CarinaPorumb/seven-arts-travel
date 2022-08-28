package ro.itschool.controller.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ro.itschool.enums.Style;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class MusicDTO {

    private String name;

    private String imageLink;

    private String author;

    @Enumerated(EnumType.STRING)
    private Style movement;

    private Boolean isTemporary;

    private LocalDateTime eventTime;

    private String location;



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

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
