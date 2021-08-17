package com.example.online_shopping.controller;

import com.example.online_shopping.database.repository.UsersRepository;
import com.example.online_shopping.erroe.ExceptionClass;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.request.user.CardRequest;
import com.example.online_shopping.services.CardServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.example.online_shopping.util.Mappings.*;

@CrossOrigin
@RestController
@RequestMapping(CARD)
public class CardController {


    private CardServices services;
    private UsersRepository repository;

    public CardController(CardServices services, UsersRepository repository) {
        this.services = services;
        this.repository = repository;
    }


    @PostMapping()
    public ResponseEntity<?> addCard(@Valid @RequestBody CardRequest card, BindingResult result, Principal principal) throws NotFound {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(services.addCard(card, principal), HttpStatus.CREATED);
    }

    @DeleteMapping("/{card_id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long card_id, Principal principal) throws NotFound {
        return new ResponseEntity<>(services.deleteCard(card_id, principal), HttpStatus.OK);
    }
}
