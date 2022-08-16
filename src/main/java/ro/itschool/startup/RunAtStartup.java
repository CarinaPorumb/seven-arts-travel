package ro.itschool.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.Architecture;
import ro.itschool.entity.Music;
import ro.itschool.entity.Role;
import ro.itschool.entity.User;
import ro.itschool.enums.Style;
import ro.itschool.service.ArchitectureService;
import ro.itschool.service.MusicService;
import ro.itschool.service.UserService;

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

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {

        saveAdmin();
        saveContributor();
        saveUser();
        saveArchitecture();


    }

    public void saveMusic() {
        Set<Music> musics = new HashSet<>();
        Music music = new Music();
        music.setName("George Enescu International Festival");
        music.setMovement(Style.CONTEMPORARY);
        music.setLocation("Romanian Athenaeum, 050204, Bucharest, Romania");
        music.setIsTemporary(true);
        music.setYear(LocalDate.of(2022, 9, 4));

        Music music2 = new Music();
        music2.setName("Vienna New Year's Concert");
        music2.setMovement(Style.ROMANTICISM);
        music2.setLocation("Golden Hall of the Musikverein in Vienna, Austria");
        music2.setIsTemporary(true);
        music2.setYear(LocalDate.of(2023, 1, 1));

        musicService.save(music);
        musicService.save(music2);
        musics.add(music);
        musics.add(music2);

    }

    public void saveArchitecture() {
        Set<Architecture> architectures = new HashSet<>();
        Architecture architecture = new Architecture();
        architecture.setName("Chateau de Chambord");
        architecture.setMovement(Style.RENAISSANCE);
        architecture.setLocation("Chateau, 41250 Chambord, France");
        architecture.setAuthor("Domenico da Cortona");
        architecture.setYear(1547);
        architecture.setIsTemporary(false);

        Architecture architecture2 = new Architecture();
        architecture2.setName("Notre-Dame de Paris");
        architecture2.setMovement(Style.GOTHIC);
        architecture2.setLocation("Parvis Notre-Dame 75004 Paris, France");
        architecture2.setAuthor("Jean de Chelles");
        architecture2.setYear(1345);
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

//        Set<Location> locations = new HashSet<>();
//        Location location = new Location();
//        location.setName("Vienna State Opera");
//        location.setCategory(Category.BalletAndTheater);
//        location.setMovement(Style.Classicism);
//        location.setIsTemporary(true);
//        location.setCountry("Austria");
//        location.setYear(2022);
//        location.setUser(user);

//        Location location2 = new Location();
//        location2.setName("The Thinker");
//        location2.setCategory(Category.Sculpture);
//        location2.setMovement(Style.REALISM);
//        location2.setIsTemporary(false);
//        location2.setCountry("France");
//        location2.setYear(1902);
//        location2.setUser(user);
//
//        locations.add(location);
//        locations.add(location2);
//        user.setLocations(locations);
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
        userService.saveUser(user);
    }
}