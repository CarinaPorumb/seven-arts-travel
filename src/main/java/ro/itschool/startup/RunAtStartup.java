package ro.itschool.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.Role;
import ro.itschool.entity.User;
import ro.itschool.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class RunAtStartup {

    @Autowired
    private UserService userService;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {

        saveAdmin();
        saveContributor();
        saveUser();


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

//        Set<Location> locations = new HashSet<>();
//        Location location = new Location();
//        location.setName("Chateau de Chambord");
//        location.setCategory(Category.Architecture);
//        location.setMovement(Style.Renaissance);
//        location.setIsTemporary(false);
//        location.setCountry("France");
//        location.setYear(1547);
//        location.setUser(user);

//        Location location2 = new Location();
//        location2.setName("Notre Dame de Paris");
//        location2.setCategory(Category.Architecture);
//        location2.setMovement(Style.Gothic);
//        location2.setIsTemporary(false);
//        location2.setCountry("France");
//        location2.setYear(1345);
//        location2.setUser(user);
//
//        locations.add(location);
//        locations.add(location2);
//        user.setLocations(locations);
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