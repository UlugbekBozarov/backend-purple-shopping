package com.example.online_shopping.controller;

import com.example.online_shopping.erroe.ExceptionClass;
import com.example.online_shopping.request.comment.CommentRequest;
import com.example.online_shopping.services.CommentServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.example.online_shopping.util.Mappings.ADMIN;
import static com.example.online_shopping.util.Mappings.COMMENT;
import static com.example.online_shopping.util.Mappings.USER;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(COMMENT)
public class CommentController {

    private CommentServices services;

    public CommentController(CommentServices services) {
        this.services = services;
    }


    @GetMapping(ADMIN + "/{user_id}")
    public ResponseEntity<?> getCommitsAdmins(@PathVariable Long user_id) {
        return new ResponseEntity<>(services.getCommentUser(user_id), HttpStatus.OK);
    }

    @GetMapping(USER)
    public ResponseEntity<?> getProductCommitsAdmins(Principal principal) {
        return new ResponseEntity<>(services.getCommentUserMe(principal.getName()), HttpStatus.OK);
    }


    @PostMapping(ADMIN + "/{user_id}")
    public ResponseEntity<?> sendMessage(@PathVariable Long user_id, @Valid @RequestBody CommentRequest comment, Principal principal, BindingResult result) {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(services.creareCommentAdmin(comment, user_id, principal.getName()), HttpStatus.OK);
    }


    @PostMapping(USER)
    public ResponseEntity<?> sendMessageUser(@Valid @RequestBody CommentRequest comment, Principal principal, BindingResult result) {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(services.creareCommentUser(comment, principal.getName()), HttpStatus.OK);
    }


    @PutMapping(USER + "/{comment_id}")
    public ResponseEntity<?> editCommentUser(@PathVariable Long comment_id, @Valid @RequestBody CommentRequest comment, BindingResult result, Principal principal) {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(services.editComment(comment_id, comment, principal.getName()), HttpStatus.OK);
    }


    @PutMapping(ADMIN + "/{comment_id}")
    public ResponseEntity<?> editCommentAdmin(@PathVariable Long comment_id, @Valid @RequestBody CommentRequest comment, BindingResult result, Principal principal) {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(services.editComment(comment_id, comment, principal.getName()), HttpStatus.OK);
    }
}
