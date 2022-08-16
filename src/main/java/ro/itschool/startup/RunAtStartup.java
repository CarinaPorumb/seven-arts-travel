package ro.itschool.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.*;
import ro.itschool.enums.Style;
import ro.itschool.service.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class RunAtStartup {

    @Autowired
    private UserService userService;
    @Autowired
    private ArchitectureService architectureService;
    @Autowired
    private MusicService musicService;
    @Autowired
    private PaintingService paintingService;
    @Autowired
    private LiteratureService literatureService;

    @Autowired
    private SculptureService sculptureService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private BalletAndTheatreService balletAndTheatreService;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {

        saveAdmin();
        saveContributor();
        saveUser();
        saveArchitecture();
        saveMusic();
        savePainting();
        saveLiterature();
        saveSculpture();
        saveCinema();

    }

    public void saveBalletAndTheatre() {
        Set<BalletAndTheatre> balletAndTheatreSet = new HashSet<>();
        BalletAndTheatre balletAndTheatre = new BalletAndTheatre();
        balletAndTheatre.setName("Swan Lake");
        balletAndTheatre.setAuthor("English National Ballet");
        balletAndTheatre.setLocation("London Coliseum, St Martin's Ln, London, UK");
        balletAndTheatre.setMovement(Style.ROMANTICISM);
        balletAndTheatre.setEventTime(LocalDate.of(2022, 9, 28));
        balletAndTheatre.setIsTemporary(true);

        balletAndTheatreService.save(balletAndTheatre);
        balletAndTheatreSet.add(balletAndTheatre);

    }

    public void saveArchitecture() {
        Set<Architecture> architectures = new HashSet<>();
        Architecture architecture = new Architecture();
        architecture.setName("Chateau de Chambord");
        architecture.setMovement(Style.RENAISSANCE);
        architecture.setLocation("Chateau, 41250 Chambord, France");
        architecture.setAuthor("Domenico da Cortona");
        architecture.setYear(LocalDate.ofEpochDay(1547));
        architecture.setIsTemporary(false);

        Architecture architecture2 = new Architecture();
        architecture2.setName("Notre-Dame de Paris");
        architecture2.setMovement(Style.GOTHIC);
        architecture2.setLocation("Parvis Notre-Dame 75004 Paris, France");
        architecture2.setAuthor("Jean de Chelles");
        architecture2.setYear(LocalDate.ofEpochDay(1345));
        architecture2.setIsTemporary(false);

        architectureService.save(architecture);
        architectureService.save(architecture2);
        architectures.add(architecture);
        architectures.add(architecture2);
    }

    public void saveAdmin() {
        User user = new User();
        user.setUsername("Admin");
        user.setPassword("Admin");
        user.setRandomToken("randomToken");
        final Role roleUserAdmin = new Role("ROLE_ADMIN");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleUserAdmin);
        user.setRoles(roles);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEmail("admin1@yahoo.com");
        user.setFullName("Admin1");
        user.setPasswordConfirm("Admin");
        user.setRandomTokenEmail("randomToken");
        userService.saveUser(user);
    }

    public void saveContributor() {
        User user = new User();
        user.setUsername("Contributor");
        user.setPassword("Contributor");
        user.setRandomToken("randomToken");
        final Set<Role> roleUserContributor = new HashSet<>();
        roleUserContributor.add(new Role("ROLE_CONTRIBUTOR"));
        user.setRoles(roleUserContributor);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEmail("contributor@yahoo.com");
        user.setFullName("Contributor1");
        user.setPasswordConfirm("Contributor");
        user.setRandomTokenEmail("randomToken");
        userService.saveUser(user);
    }

    public void saveUser() {
        User user = new User();
        user.setUsername("User");
        user.setPassword("User");
        user.setRandomToken("randomToken");
        final Set<Role> roleUserUser = new HashSet<>();
        roleUserUser.add(new Role("ROLE_USER"));
        user.setRoles(roleUserUser);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEmail("user@yahoo.com");
        user.setFullName("User");
        user.setPasswordConfirm("User");
        user.setRandomTokenEmail("randomToken");
        userService.saveUser(user);
    }

    public void saveMusic() {
        Set<Music> musics = new HashSet<>();
        Music music = new Music();
        music.setName("George Enescu International Festival");
        music.setMovement(Style.CONTEMPORARY);
        music.setLocation("Romanian Athenaeum, 050204, Bucharest, Romania");
        music.setIsTemporary(true);
        music.setEventTime(LocalDate.of(2022, 9, 4));

        Music music2 = new Music();
        music2.setName("Vienna New Year's Concert");
        music2.setMovement(Style.ROMANTICISM);
        music2.setLocation("Golden Hall of the Vienna State Opera, Austria");
        music2.setIsTemporary(true);
        music2.setEventTime(LocalDate.of(2023, 1, 1));

        musicService.save(music);
        musicService.save(music2);
        musics.add(music);
        musics.add(music2);
    }

    public void savePainting() {
        Set<Painting> paintings = new HashSet<>();
        Painting painting = new Painting();
        painting.setName("L'Annonciade Museum");
        painting.setMovement(Style.MODERNISM);
        painting.setLocation("Georges Grammont, 83990 Saint-Tropez, France");
        painting.setIsTemporary(false);
        painting.setYear(LocalDate.ofEpochDay(1922));

        Painting painting2 = new Painting();
        painting2.setName("The Louvre Museum");
        painting2.setMovement(Style.RENAISSANCE);
        painting2.setLocation("Rue de Rivoli, 75001 Paris, France");
        painting2.setIsTemporary(false);
        painting2.setYear(LocalDate.ofEpochDay(1793));

        paintingService.save(painting);
        paintingService.save(painting2);
        paintings.add(painting);
        paintings.add(painting2);
    }

    public void saveLiterature() {
        Set<Literature> literatures = new HashSet<>();
        Literature literature = new Literature();
        literature.setName("Livraria Lello & Irmão");
        literature.setLocation("R. das Carmelitas 144, 4050-161 Porto, Portugal");
        literature.setMovement(Style.ARTNOUVEAU);
        literature.setIsTemporary(false);
        literature.setYear(LocalDate.ofEpochDay(1906));

        Literature literature2 = new Literature();
        literature2.setName("Strahov Library");
        literature2.setLocation("Strahovské nádvoří 132/1, 118 00 Praha, Czech Republic");
        literature2.setMovement(Style.GOTHIC);
        literature2.setIsTemporary(false);
        literature2.setYear(LocalDate.ofEpochDay(1138));

        literatureService.save(literature);
        literatureService.save(literature2);
        literatures.add(literature);
        literatures.add(literature2);
    }

    public void saveSculpture() {
        Set<Sculpture> sculptures = new HashSet<>();
        Sculpture sculpture = new Sculpture();
        sculpture.setName("The Thinker");
        sculpture.setAuthor("Auguste Rodin");
        sculpture.setLocation("77 Rue de Varenne, 75007 Paris, France");
        sculpture.setMovement(Style.REALISM);
        sculpture.setIsTemporary(false);
        sculpture.setYear(LocalDate.ofEpochDay(1902));

        Sculpture sculpture2 = new Sculpture();
        sculpture2.setName("Bird in Space");
        sculpture2.setAuthor("Constantin Brancusi");
        sculpture2.setLocation("1000 5th Ave, New York, 10028, USA");
        sculpture2.setMovement(Style.MODERNISM);
        sculpture2.setIsTemporary(false);
        sculpture2.setYear(LocalDate.ofEpochDay(1923));

        sculptureService.save(sculpture);
        sculptureService.save(sculpture2);
        sculptures.add(sculpture);
        sculptures.add(sculpture2);
    }

    public void saveCinema() {
        Set<Cinema> cinemas = new HashSet<>();
        Cinema cinema = new Cinema();
        cinema.setName("Cannes Film Festival");
        cinema.setLocation("Palace of Festivals and Congresses of Cannes, France");
        cinema.setMovement(Style.CONTEMPORARY);
        cinema.setYear(LocalDate.of(2023, 5, 16));
        cinema.setIsTemporary(true);

        Cinema cinema2 = new Cinema();
        cinema2.setName("The National Museum of Cinema");
        cinema2.setLocation("Via Montebello, 20, 10124 Torino, Italy");
        cinema2.setMovement(Style.CONTEMPORARY);
        cinema2.setYear(LocalDate.ofEpochDay(1958));
        cinema2.setIsTemporary(false);

        cinemaService.save(cinema);
        cinemaService.save(cinema2);
        cinemas.add(cinema);
        cinemas.add(cinema2);
    }
}


//        Set<Location> locations = new HashSet<>();
//        Location location = new Location();
//        location.setName("Strahov Library");
//        location.setCategory(Category.Literature);
//        location.setMovement(Style.Gothic);
//        location.setIsTemporary(true);
//        location.setCountry("Czech Republic");
//        location.setYear(1138);
//        location.setUser(user);
//
//        Location location2 = new Location();
//        location2.setName("The Louvre Museum");
//        location2.setCategory(Category.Painting);
//        location2.setMovement(Style.Renaissance);
//        location2.setIsTemporary(false);
//        location2.setCountry("France");
//        location2.setYear(1793);
//        location2.setUser(user);
//
//        locations.add(location);
//        locations.add(location2);
//        user.setLocations(locations);