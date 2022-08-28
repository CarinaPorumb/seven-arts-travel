package ro.itschool.controller.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ro.itschool.enums.Style;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CinemaDTO {

    private String name;

    private String imageLink;

    @Enumerated(EnumType.STRING)
    private Style movement;

    private Boolean isTemporary;

    private LocalDate year;

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

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
