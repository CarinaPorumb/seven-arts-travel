package ro.itschool.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArtObjectTest {

    ArtObject artObject;

    @BeforeEach
    void setUp() {
        artObject = new ArtObject();
    }

    @Test
    void getName() {
        String name = "Chateau";
        artObject.setName(name);
        assertEquals(name, artObject.getName());
    }

    @Test
    void getId() {
        Long id = 1L;
        artObject.setId(id);
        assertEquals(id, artObject.getId());
    }
}