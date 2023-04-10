package ro.project.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import ro.project.entity.ArtEvent;
import ro.project.entity.ArtObject;
import ro.project.entity.Role;
import ro.project.entity.User;
import ro.project.enums.Category;
import ro.project.enums.Style;
import ro.project.model.ArtEventCSV;
import ro.project.model.ArtObjectCSV;
import ro.project.repository.ArtEventRepository;
import ro.project.repository.ArtObjectRepository;
import ro.project.service.ArtEventService;
import ro.project.service.ArtObjectService;
import ro.project.service.UserService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class RunAtStartup {
    private final UserService userService;
    private final ArtObjectService artObjectService;
    private final ArtEventService artEventService;
    private final ArtEventRepository artEventRepository;
    private final ArtObjectRepository artObjectRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() throws FileNotFoundException {
        saveAdmin();
        saveUser();
        loadDataFromCSVArtObject();
        loadDataFromCSVArtEvent();
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
        user.setEmail("admin@gmail.com");
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
        user.setEmail("contributor@gmail.com");
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
        user.setEmail("user@gmail.com");
        user.setFullName("User");
        user.setPasswordConfirm("User");
        user.setRandomTokenEmail("randomToken");
        userService.saveUser(user);
    }

    public void loadDataFromCSVArtObject() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/artobject.csv");
        List<ArtObjectCSV> records = artObjectService.convertCSV(file);
        records.forEach(artObjectCSV -> {
            Category category = switch (artObjectCSV.getCategory().toLowerCase()) {
                case "architecture" -> Category.ARCHITECTURE;
                case "sculpture" -> Category.SCULPTURE;
                case "painting" -> Category.PAINTING;
                case "literature" -> Category.LITERATURE;
                default -> Category.NO_CATEGORY;
            };
            Style movement = switch (artObjectCSV.getMovement().toLowerCase()) {
                case "renaissance" -> Style.RENAISSANCE;
                case "gothic" -> Style.GOTHIC;
                case "baroque" -> Style.BAROQUE;
                case "artnouveau", "art-nouveau", "art_nouveau" -> Style.ARTNOUVEAU;
                case "realism" -> Style.REALISM;
                case "ancient" -> Style.ANCIENT;
                case "classicism" -> Style.CLASSICISM;
                case "romanticism" -> Style.ROMANTICISM;
                case "modernism" -> Style.MODERNISM;
                case "contemporary" -> Style.CONTEMPORARY;
                case "neoclassicism" -> Style.NEOCLASSICISM;
                case "avantgarde", "avant-garde", "avant_garde" -> Style.AVANTGARDE;
                default -> Style.UNCERTAIN;
            };
            artObjectRepository.save(ArtObject.builder()
                    .name(artObjectCSV.getName())
                    .author(artObjectCSV.getAuthor())
                    .location(artObjectCSV.getLocation())
                    .imageLink(artObjectCSV.getImageLink())
                    .year(artObjectCSV.getYear())
                    .isTemporary(artObjectCSV.isTemporary())
                    .category(category)
                    .movement(movement)
                    .build());
        });
    }

    public void loadDataFromCSVArtEvent() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/artevent.csv");
        List<ArtEventCSV> records = artEventService.convertCSV(file);

        records.forEach(artEventCSV -> {
            Category category = switch (artEventCSV.getCategory().toLowerCase()) {
                case "literature" -> Category.LITERATURE;
                case "sculpture" -> Category.SCULPTURE;
                case "music" -> Category.MUSIC;
                case "cinema" -> Category.CINEMA;
                case "painting" -> Category.PAINTING;
                case "ballet and theatre", "ballet", "theatre", "ballet & theatre", "ballet-and-theatre",
                        "ballet_and_theatre" -> Category.BALLET_AND_THEATRE;
                default -> Category.NO_CATEGORY;
            };
            Style movement = switch (artEventCSV.getMovement().toLowerCase()) {
                case "baroque" -> Style.BAROQUE;
                case "classicism" -> Style.CLASSICISM;
                case "romanticism" -> Style.ROMANTICISM;
                case "modernism" -> Style.MODERNISM;
                case "contemporary" -> Style.CONTEMPORARY;
                case "avantgarde", "avant-garde", "avant_garde" -> Style.AVANTGARDE;
                default -> Style.UNCERTAIN;
            };
            artEventRepository.save(ArtEvent.builder()
                    .name(artEventCSV.getName())
                    .location(artEventCSV.getLocation())
                    .imageLink(artEventCSV.getImageLink())
                    .isTemporary(artEventCSV.isTemporary())
                    .category(category)
                    .movement(movement)
                    .build()
            );
        });
    }

}