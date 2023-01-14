package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.itschool.controller.model.UserDTO;
import ro.itschool.entity.Role;
import ro.itschool.entity.User;
import ro.itschool.repository.RoleRepository;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    MailSender mailSender;

    @GetMapping("/users")
    public String getAllUsers(Model model, String keyword) throws Exception {
        model.addAttribute("users", userService.searchUser(keyword));
        model.addAttribute("adminRole", roleRepository.findAll()
                .stream()
                .map((Role::getName))
                .filter(role -> role.equals("ROLE_ADMIN"))
                .findAny()
                .orElseThrow(() -> new Exception("User with admin roles not found")));

        return "all-users";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteUserById(Model model, @PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }

    @RequestMapping("/add-admin-role/{id}")
    public String addAdminRoleToUser(@PathVariable("id") Long id) {
        final Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            final Role role = roleRepository.findByName("ROLE_ADMIN");
            user.get().getRoles().add(role);
            userService.updateUser(user.get());
            return "redirect:/users";
        }
        return ("USER not found for this id : " + id);
    }

    @RequestMapping("/remove-admin-role/{id}")
    public String removeAdminRoleFromUser(@PathVariable("id") Long id) {
        String username = getCurrentUserDetails();
        final Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            final Role role = roleRepository.findByName("ROLE_ADMIN");
            user.get().getRoles().remove(role);
            userService.updateUser(user.get());
            //Check if logged user is the same as selected user
            if (username.equals(user.get().getUsername()))
                return "redirect:/logout";
            else
                return "redirect:/users";
        }
        return ("USER not found for this id : " + id);
    }

    private String getCurrentUserDetails() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        return loggedInUser.getName();
    }

    //POSTMAN GET ALL USERS
    @GetMapping("/users/postman")
    @ResponseBody
    public List<UserDTO> getAllUsersForPostman() {
        return userService.findAll();
    }

}