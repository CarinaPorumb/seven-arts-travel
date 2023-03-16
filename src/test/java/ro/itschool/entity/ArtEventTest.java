package ro.itschool.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArtEventTest {

    ArtEvent artEvent;

    @BeforeEach
    void setUp() {
        artEvent = new ArtEvent();
    }

    @Test
    void getId() {
        Integer id = 1;
        artEvent.setId(id);
        assertEquals(id, artEvent.getId());
    }

    @Test
    void getName() {
        String name = "Event";
        artEvent.setName(name);
        assertEquals(name, artEvent.getName());
    }
}