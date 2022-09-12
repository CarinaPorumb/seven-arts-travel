package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.entity.Contact;
import ro.itschool.repository.ContactRepository;

@Controller
public class ContactController {

    @Autowired
    ContactRepository contactRepository;

    @RequestMapping(value = {"/save-contact"})
    public String saveContact(Model model) {
        model.addAttribute("contact", new Contact());
        return "save-contact";
    }

    @PostMapping("/save-contact")
    public String saveContact2(@ModelAttribute Contact contact, Model model) {
        model.addAttribute("contact", contact);
        contactRepository.save(contact);
        return "redirect:/all-contacts";
    }

}
