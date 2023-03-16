package ro.itschool.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import ro.itschool.entity.ArtObject;
import ro.itschool.service.ArtObjectService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ArtObjectControllerTest {

    @Mock
    ArtObjectService artObjectService;

    @Mock
    Model model;

    ArtObjectController artObjectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        artObjectController = new ArtObjectController(artObjectService);
    }

    @Test
    void getArtObjects() throws Exception {
        ArtObject artObject = new ArtObject();
        artObject.setName("Bla");
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(artObjectController).build();
        when(artObjectService.searchArtObject(anyString())).thenReturn(Collections.singletonList(artObject));
        mockMvc.perform(MockMvcRequestBuilders.get("/artobject-list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/artobject"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("artobjects"));
    }

    @Test
    void saveArtObject1() {
    }

    @Test
    void saveArtObject2() {
    }

    @Test
    void updateArtObject() {
    }

    @Test
    void deleteArtObject() {
    }

    @Test
    void displayArchitecture() {
    }

    @Test
    void displaySculpture() {
    }

    @Test
    void displayLiterature() {
    }

    @Test
    void displayPainting() {
    }
}