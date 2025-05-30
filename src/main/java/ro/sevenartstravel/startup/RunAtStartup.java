package ro.sevenartstravel.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.sevenartstravel.service.entity.impl.ArtObjectServiceImpl;

import java.io.File;

@RequiredArgsConstructor
@Component
public class RunAtStartup {

    private final ArtObjectServiceImpl artObjectServiceImpl;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        String artWorkPath = "ro/main/resources/csvdata/artwork.csv";
        File artWorkFile = new File(artWorkPath);
        artObjectServiceImpl.convertCSV(artWorkFile);

    }



}