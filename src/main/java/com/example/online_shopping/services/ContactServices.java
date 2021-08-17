package com.example.online_shopping.services;

import com.example.online_shopping.database.domain.UserContact;
import com.example.online_shopping.database.domain.Users;
import com.example.online_shopping.database.repository.UserContactRepo;
import com.example.online_shopping.database.repository.UsersRepository;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.erroe.message.ErrorMessage;
import com.example.online_shopping.request.user.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Slf4j
@Service
public class ContactServices {

    private UsersRepository usersRepository;

    private UserContactRepo contactRepo;

    @Autowired
    public ContactServices(UsersRepository usersRepository, UserContactRepo contactRepo) {
        this.usersRepository = usersRepository;
        this.contactRepo = contactRepo;
    }


    public Users addContact(Contact addContact, Principal principal) throws NotFound {
        try {
            Users user = usersRepository.findByUsername(principal.getName());
            UserContact contact = new UserContact(user.getUser_id(), addContact.getContact());
            contactRepo.save(contact);
            log.info("Create Contact: {}", contact.toString());
            return user;
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The contact has not been saved!");
            throw new NotFound("Save Contact", -2, error);
        }
    }

    public Users editContact(Long contact_id, Contact editContact, Principal principal) throws NotFound {
        ErrorMessage error;

        try {
            Users user = usersRepository.findByUsername(principal.getName());
            UserContact contact = contactRepo.findById(contact_id).get();
            if (contact.getUser_id() == user.getUser_id()) {
                contact.setContact(editContact.getContact());
                contactRepo.save(contact);
                log.info("Edit Contact: {}", contact.toString());
            } else {
                error = new ErrorMessage(406, "Bunday Contact topilmadi");
                throw new NotFound("Edit Contact", -2, error);
            }

            return user;
        } catch (Exception e) {
            error = new ErrorMessage(406, "The contact has not been edited!");
            throw new NotFound("Edit Contact", -2, error);
        }
    }

    public Users deleteContact(Long contact_id, Principal principal) throws NotFound {
        ErrorMessage error;

        try {
            Users user = usersRepository.findByUsername(principal.getName());
            UserContact contact = contactRepo.findById(contact_id).get();
            if (contact.getUser_id() == user.getUser_id()) {
                contactRepo.deleteById(contact_id);
                log.info("Delete Contact: {}", contact.toString());
            } else {
                error = new ErrorMessage(406, "Bunday Address topilmadi");
                throw new NotFound("Delete Contact", -2, error);
            }

            return user;
        } catch (Exception e) {
            error = new ErrorMessage(406, "The contact has not been deleted!");
            throw new NotFound("Delete Contact", -2, error);
        }
    }
}
