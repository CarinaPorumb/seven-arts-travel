package ro.sevenartstravel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ro.sevenartstravel.dto.UserCreateDTO;
import ro.sevenartstravel.dto.UserDTO;
import ro.sevenartstravel.dto.UserUpdateDTO;
import ro.sevenartstravel.entity.User;
import ro.sevenartstravel.exception.NotFoundException;
import ro.sevenartstravel.repository.RoleRepository;
import ro.sevenartstravel.repository.UserRepository;
import ro.sevenartstravel.service.crud.CrudService;
import ro.sevenartstravel.service.entity.UserService;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final CrudService<UserDTO, UUID> crudService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            Pageable pageable) {

        if (search != null && !search.isEmpty()) {
            log.info("Searching users with keyword: {}", search);
            return ResponseEntity.ok(userService.searchUser(search, pageable));
        } else if ((username != null && !username.isEmpty()) || (fullName != null && !fullName.isEmpty()) || (email != null && !email.isEmpty())) {
            log.info("Listing users with filters: username='{}', fullName='{}', email='{}'", username, fullName, email);
            return ResponseEntity.ok(userService.getAllUsers(username, fullName, email, pageable));
        } else {
            log.info("Returning all users without filters.");
            return ResponseEntity.ok(crudService.getAll(pageable));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") UUID id) {
        log.info("Request to get user by id: {}", id);
        return ResponseEntity.ok(crudService.getById(id)
                .orElseThrow(() -> new NotFoundException("User", id)));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserCreateDTO userDTO) {
        log.info("Registering user: {}", userDTO);
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    // PUT / Update user by admin
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserByAdmin(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO userDTO) {
        log.info("Admin updating user with id: {}", id);
        userService.updateUserByAdmin(userDTO);
        return ResponseEntity.ok().build();
    }

    // PATCH / User updating their own profile
    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(@Valid @RequestBody UserUpdateDTO userDTO) {
        log.info("User updating their profile: {}", userDTO);
        userService.updateProfile(userDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        log.info("Request to delete user with id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/roles/admin")
    public ResponseEntity<Void> addAdminRoleToUser(@PathVariable UUID id) {
        log.info("Request to add ADMIN role to user with id: {}", id);
        userService.addAdminRole(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/roles/admin")
    public ResponseEntity<Void> removeAdminRoleFromUser(@PathVariable("id") UUID userId) {
        log.info("Request to remove ADMIN role from user with id: {}", userId);

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> user = userService.findUserEntityByUsername(currentUsername);

        if (user.isPresent() && user.get().getId().equals(userId)) {
            log.warn("Attempt to remove own ADMIN role denied for user {}", currentUsername);
            return ResponseEntity.status(403).build();
        }

        userService.removeAdminRole(userId);
        return ResponseEntity.noContent().build();
    }

    private String getCurrentUserDetails() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        return loggedInUser.getName();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/roles/{role}")
    public ResponseEntity<Void> assignRole(
            @PathVariable UUID id,
            @PathVariable String role) {
        userService.addRoleToUser(id, "ROLE_" + role.toUpperCase());
        return ResponseEntity.ok().build();
    }

}
