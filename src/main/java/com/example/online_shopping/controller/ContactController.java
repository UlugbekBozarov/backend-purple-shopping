package com.example.online_shopping.controller;

import com.example.online_shopping.erroe.ExceptionClass;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.request.user.Contact;
import com.example.online_shopping.services.ContactServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.example.online_shopping.util.Mappings.*;

@CrossOrigin
@RestController
@RequestMapping(CONTACT)
public class ContactController {

    private final ContactServices services;

    public ContactController(ContactServices services) {
        this.services = services;
    }

    @PostMapping()
    public ResponseEntity<?> addContact(@Valid @RequestBody Contact contact, BindingResult result, Principal principal) throws NotFound {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(services.addContact(contact, principal), HttpStatus.CREATED);
    }

    @PutMapping("/{contact_id}")
    public ResponseEntity<?> editContact(@PathVariable Long contact_id, @Valid @RequestBody Contact contact, BindingResult result, Principal principal) throws NotFound {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(services.editContact(contact_id, contact, principal), HttpStatus.OK);
    }

    @DeleteMapping("/{contact_id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long contact_id, Principal principal) throws NotFound {
        return new ResponseEntity<>(services.deleteContact(contact_id, principal), HttpStatus.OK);
    }
}
