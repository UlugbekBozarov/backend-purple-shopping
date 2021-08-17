package com.example.online_shopping.controller;

import com.example.online_shopping.erroe.ExceptionClass;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.request.user.Address;
import com.example.online_shopping.services.AddressServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.example.online_shopping.util.Mappings.*;

@CrossOrigin
@RestController
@RequestMapping(ADDRESS)
public class AddressController {

    private final AddressServices services;

    public AddressController(AddressServices services) {
        this.services = services;
    }

    @PostMapping()
    public ResponseEntity<?> addAddress(@Valid @RequestBody Address addAddress, BindingResult result, Principal principal) throws NotFound {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(services.addAddress(addAddress, principal), HttpStatus.CREATED);
    }

    @PutMapping("/{address_id}")
    public ResponseEntity<?> editAddress(@PathVariable Long address_id, @Valid @RequestBody Address editAddress, BindingResult result, Principal principal) throws NotFound {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(services.editAddress(address_id, editAddress, principal), HttpStatus.OK);
    }

    @DeleteMapping("/{address_id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long address_id, Principal principal) throws NotFound {
        return new ResponseEntity<>(services.deleteAddress(address_id, principal), HttpStatus.OK);
    }
}
