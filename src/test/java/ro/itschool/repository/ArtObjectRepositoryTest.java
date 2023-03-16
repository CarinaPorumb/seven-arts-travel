package ro.itschool.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.itschool.entity.ArtObject;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArtObjectRepositoryTest {

    @Mock
    ArtObjectRepository artObjectRepository;

    ArtObject artObject;

    ArtObject artObject2;

    @BeforeEach
    void setUp() {
        artObject = new ArtObject();
        artObject.setId(1L);
        artObject.setName("Chateau");

        artObject2 = new ArtObject();
        artObject2.setId(2L);
        artObject2.setName("Library");
    }

    @Test
    void findAll() {
        List<ArtObject> artObjectOptional = new ArrayList<>();
        artObjectOptional.add(artObject);
        artObjectOptional.add(artObject2);
        when(artObjectRepository.findAll()).thenReturn(artObjectOptional);
        assertNotNull(artObjectOptional);
        assertEquals(2, artObjectOptional.size());
    }

    @Test
    void findByName() {
        when(artObjectRepository.findByName(any())).thenReturn(artObject);
        ArtObject chateau = artObjectRepository.findByName("Chateau");
        assertEquals("Chateau", chateau.getName());
        verify(artObjectRepository).findByName(any());
    }

    @Test
    void deleteByName() {
        artObjectRepository.deleteByName("Library");
        verify(artObjectRepository).deleteByName(anyString());
    }

    @Test
    void searchArtObject() {
        artObjectRepository.searchArtObject("Library");
        verify(artObjectRepository).searchArtObject(anyString());
    }

    @Test
    void displayArchitecture() {
        artObjectRepository.displayArchitecture("Chateau");
        verify(artObjectRepository).displayArchitecture(anyString());
    }

    @Test
    void displaySculpture() {
        artObjectRepository.displaySculpture("Library");
        verify(artObjectRepository).displaySculpture(anyString());
    }

    @Test
    void displayPainting() {
        artObjectRepository.displayPainting("Chateau");
        verify(artObjectRepository).displayPainting(anyString());
    }

    @Test
    void displayLiterature() {
        artObjectRepository.displayLiterature("Library");
        verify(artObjectRepository).displayLiterature(anyString());
    }

    @Test
    void findByUserId() {
    }
}