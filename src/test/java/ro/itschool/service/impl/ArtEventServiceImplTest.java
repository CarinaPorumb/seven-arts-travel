package ro.itschool.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.itschool.entity.ArtEvent;
import ro.itschool.repository.ArtEventRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtEventServiceImplTest {

    @Mock
    ArtEventRepository artEventRepository;
    @InjectMocks
    ArtEventServiceImpl artEventServiceImpl;

    ArtEvent artEvent1;
    ArtEvent artEvent2;

    @BeforeEach
    void setUp() {
        artEvent1 = new ArtEvent();
        artEvent1.setId(1);
        artEvent1.setName("Music");

        artEvent2 = new ArtEvent();
        artEvent2.setId(2);
        artEvent2.setName("Ballet");
    }

    @Test
    void findByName() throws Exception {
        when(artEventRepository.findByName(any())).thenReturn(artEvent1);
        ArtEvent music = artEventServiceImpl.findByName("Music");
        assertEquals("Music", music.getName());
        verify(artEventRepository).findByName(any());
    }

    @Test
    void getAll() throws Exception {
        List<ArtEvent> artEventList = new ArrayList<>();
        artEventList.add(artEvent1);
        artEventList.add(artEvent2);
        when(artEventRepository.findAll()).thenReturn(artEventList);

        List<ArtEvent> artEvents = artEventServiceImpl.getAll();
        assertNotNull(artEvents);
        assertEquals(2, artEvents.size());
    }

    @Test
    void save() throws Exception {
        ArtEvent artEventToSave = new ArtEvent();
        when(artEventRepository.save(any())).thenReturn(artEvent1);
        ArtEvent savedArtEvent = artEventServiceImpl.save(artEventToSave);
        assertNotNull(savedArtEvent);
        verify(artEventRepository).save(any());
    }

    @Test
    void deleteByName() throws Exception {
        artEventServiceImpl.deleteByName("Ballet");
        verify(artEventRepository).deleteByName(anyString());
    }

    @Test
    void searchArtEvent() throws Exception {
        artEventServiceImpl.searchArtEvent("Ballet");
        verify(artEventRepository).searchArtEvent(anyString());
    }

    @Test
    void displayMusic() throws Exception {
        artEventServiceImpl.displayMusic("Music");
        verify(artEventRepository).displayMusic(anyString());
    }

    @Test
    void displayCinema() throws Exception {
        artEventServiceImpl.displayCinema("Music");
        verify(artEventRepository).displayCinema(anyString());
    }

    @Test
    void displayBalletAndTheatre() throws Exception {
        artEventServiceImpl.displayBalletAndTheatre("Ballet");
        verify(artEventRepository).displayBalletAndTheatre(anyString());
    }
}