package ro.itschool.controller.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.itschool.enums.Style;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SculptureDTO {

    private String name;

    private String imageLink;

    private String author;

    @Enumerated(EnumType.STRING)
    private Style movement;

    private Boolean isTemporary;

    private Integer year;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
