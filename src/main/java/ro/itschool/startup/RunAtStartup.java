package ro.itschool.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.Location;
import ro.itschool.entity.Role;
import ro.itschool.entity.User;
import ro.itschool.enums.Category;
import ro.itschool.enums.Movement;
import ro.itschool.service.UserService;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


@Component
public class RunAtStartup {

    final Role roleUserAdmin = new Role("ROLE_ADMIN");
    final Role roleUserContributor = new Role("ROLE_CONTRIBUTOR");
    final Role roleUserUser = new Role("ROLE_USER");
    final Set<Role> roles = new HashSet<>();

    @Autowired
    private UserService userService;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {

        saveAdmin();
        saveContributor();
        saveUser();

    }

    public void saveAdmin(){
        User user = new User();
        user.setUsername("Admin");
        user.setPassword("Admin");
        user.setRandomToken("randomToken");
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

        Set<Location> locations = new HashSet<>();
        Location location = new Location();
        location.setName("Chateau de Chambord");
        location.setCategory(Category.Architecture);
        location.setMovement(Movement.Renaissance);
        location.setIsTemporary(false);
        location.setCountry("France");
        location.setYear(1547);
        location.setUser(user);

        Set<Location> locations2 = new HashSet<>();
        Location location2 = new Location();
        location.setName("Notre Dame de Paris");
        location.setCategory(Category.Architecture);
        location.setMovement(Movement.Gothic);
        location.setIsTemporary(false);
        location.setCountry("France");
        location.setYear(1345);
        location.setUser(user);

        locations.add(location);
        locations.add(location2);
        user.setLocations(locations);
        userService.saveUser(user);
    }

    public void saveContributor(){
        User user = new User();
        user.setUsername("Contributor");
        user.setPassword("Contributor");
        user.setRandomToken("randomToken");
        roles.add(roleUserContributor);
        user.setRoles(roles);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEmail("contributor@yahoo.com");
        user.setFullName("Contributor1");
        user.setPasswordConfirm("Contributor");
        user.setRandomTokenEmail("randomToken");

        Set<Location> locations = new HashSet<>();
        Location location = new Location();
        location.setName("Vienna State Opera");
        location.setCategory(Category.BalletAndTheater);
        location.setMovement(Movement.Classicism);
        location.setIsTemporary(true);
        location.setCountry("Austria");
        location.setYear(2022);
        location.setUser(user);

        Set<Location> locations2 = new HashSet<>();
        Location location2 = new Location();
        location.setName("The Thinker");
        location.setCategory(Category.Sculpture);
        location.setMovement(Movement.Realism);
        location.setCountry("France");
        location.setYear(1902);
        location.setUser(user);

        locations.add(location);
        locations.add(location2);
        user.setLocations(locations);
        userService.saveUser(user);
    }

    public void saveUser(){
        User user = new User();
        user.setUsername("User");
        user.setPassword("User");
        user.setRandomToken("randomToken");
        roles.add(roleUserUser);
        user.setRoles(roles);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEmail("user@yahoo.com");
        user.setFullName("User");
        user.setPasswordConfirm("User");
        user.setRandomTokenEmail("randomToken");

        Set<Location> locations = new HashSet<>();
        Location location = new Location();
        location.setName("Strahov Library");
        location.setCategory(Category.Literature);
        location.setMovement(Movement.Gothic);
        location.setIsTemporary(true);
        location.setCountry("Czech Republic");
        location.setYear(1138);
        location.setUser(user);

        Set<Location> locations2 = new HashSet<>();
        Location location2 = new Location();
        location.setName("The Louvre Museum");
        location.setCategory(Category.Painting);
        location.setMovement(Movement.Renaissance);
        location.setCountry("France");
        location.setYear(1793);
        location.setUser(user);

        locations.add(location);
        locations.add(location2);
        user.setLocations(locations);
        userService.saveUser(user);
    }
}