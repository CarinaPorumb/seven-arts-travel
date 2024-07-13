package ro.project.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.project.service.entity.impl.ArtEventServiceImpl;
import ro.project.service.entity.impl.ArtWorkServiceImpl;

import java.io.File;

@RequiredArgsConstructor
@Component
public class RunAtStartup {

    private final ArtWorkServiceImpl artWorkServiceImpl;
    private final ArtEventServiceImpl artEventServiceImpl;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        String artWorkPath = "src/main/resources/csvdata/artwork.csv";
        File artWorkFile = new File(artWorkPath);
        artWorkServiceImpl.convertCSV(artWorkFile);


        String artEventPath = "src/main/resources/csvdata/artevent.csv";
        File artEventFile = new File(artEventPath);
        artEventServiceImpl.convertCSV(artEventFile);
    }



}