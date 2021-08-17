package com.example.online_shopping.controller;

import com.example.online_shopping.database.domain.enumeration.VAUCHER;
import com.example.online_shopping.database.repository.VaucherRepo;
import com.example.online_shopping.erroe.ExceptionClass;
import com.example.online_shopping.request.VaucherReq;
import com.example.online_shopping.services.VaucherServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.example.online_shopping.util.Mappings.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping(VAUCHER)
public class VaucherController {

    private VaucherRepo vaucherRepo;

    private VaucherServices services;

    public VaucherController(VaucherRepo vaucherRepo, VaucherServices services) {
        this.vaucherRepo = vaucherRepo;
        this.services = services;
    }

    @PostMapping(VERIFY)
    public ResponseEntity<?> verifyVaucher(@RequestBody VaucherReq vaucher, BindingResult result, Principal principal) {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(services.verifyVaucher(vaucher.getVaucher_code()), HttpStatus.CREATED);
    }

}
