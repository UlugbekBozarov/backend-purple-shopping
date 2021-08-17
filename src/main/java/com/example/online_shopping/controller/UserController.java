package com.example.online_shopping.controller;

import com.example.online_shopping.erroe.ExceptionClass;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.erroe.SystemError;
import com.example.online_shopping.request.CreateAdmin;
import com.example.online_shopping.request.UpdatePassword;
import com.example.online_shopping.request.user.*;
import com.example.online_shopping.security.LoginViewModel;
import com.example.online_shopping.services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.example.online_shopping.util.Mappings.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(USER)
public class UserController {

    private final UserServices services;

    public UserController(UserServices services) {
        this.services = services;
    }

    @GetMapping(PROJECT_EXPERT)
    public ResponseEntity<?> getProject() throws SystemError {
        return new ResponseEntity<>(services.createExpert(), HttpStatus.OK);
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<?> signUpUser(@Valid @RequestBody SignUp user, BindingResult result) throws SystemError {

        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(services.signUpUser(user), HttpStatus.OK);
    }

    @GetMapping(ME)
    public ResponseEntity<?> getMe(Principal principal) throws SystemError {
        return new ResponseEntity<>(services.getMe(principal.getName()), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUser user, BindingResult result, Principal userPrincipal) throws SystemError {

        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(services.updateUser(user, userPrincipal.getName()), HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePassword password, BindingResult result, Principal userPrincipal) throws SystemError {

        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(services.updatePassword(userPrincipal.getName(), password.getPassword()), HttpStatus.OK);
    }

    @PostMapping(SIGN_UP + ADMIN)
    public ResponseEntity<?> createAdmin(@Valid @RequestBody CreateAdmin admin, BindingResult result, Principal userPrincipal) throws SystemError {

        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(services.createAdmin(admin.getUsername(), admin.getEmail(), admin.getPassword()), HttpStatus.OK);
    }

    @PutMapping("/update-password-user")
    public ResponseEntity<?> updatePasswordAdmin(@Valid @RequestBody LoginViewModel loginViewModel, BindingResult result, Principal userPrincipal) throws SystemError {

        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(services.updatePasswordRoleAdmin(userPrincipal.getName(), loginViewModel), HttpStatus.OK);
    }

    @PutMapping("/update-password-admin")
    public ResponseEntity<?> updatePasswordExpert(@Valid @RequestBody LoginViewModel loginViewModel, BindingResult result, Principal userPrincipal) throws SystemError {

        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(services.updateAdminPassword(loginViewModel.getUsername(), loginViewModel.getPassword()), HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<?> getUsers() throws SystemError {
        return new ResponseEntity<>(services.getUsers(), HttpStatus.OK);
    }




//    C a r d


//
//    @GetMapping()
//    public ResponseEntity<?> getUsers() throws SystemError {
//        return new ResponseEntity<>(services.getUsers(), HttpStatus.OK);
//    }
//
//
//    @GetMapping(COMMENT)
//    public ResponseEntity<?> getUsersComment() throws SystemError {
//        return new ResponseEntity<>(services.getUsersComment(), HttpStatus.OK);
//    }
//
//
//    @GetMapping("/username")
//    public ResponseEntity<?> getUserByUsername(Principal userPrincipal) throws SystemError {
//        return new ResponseEntity<>(services.getUserByUsername(userPrincipal.getName()), HttpStatus.OK);
//    }
//
//
//    @PostMapping(SIGN_UP + ADMIN)
//    public ResponseEntity<?> signUpAdmin(@Valid @RequestBody SignUp user, BindingResult result) throws SystemError {
//
//        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
//        if (errors != null) {
//            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<>(services.signUpAdmin(user), HttpStatus.OK);
//    }





//
//
//    @PutMapping(CONFIRM_PASSWORD)
//    public ResponseEntity<?> confirmPassword(@Valid @RequestBody SignUp confirmPassword, BindingResult result, Principal userPrincipal) throws SystemError {
//
//        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
//        if (errors != null) {
//            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<>(services.confirmPassword(confirmPassword, userPrincipal.getName()), HttpStatus.OK);
//    }
//
//
//    @PutMapping(STATUS_ACTIVE + "/{member_id}")
//    public ResponseEntity<?> memberStatusActive(@PathVariable Long member_id) throws SystemError {
//        return new ResponseEntity<>(services.setStatusUserActive(member_id), HttpStatus.OK);
//    }
//
//    @PutMapping(Mappings.STATUS_DEACTIVE + "/{member_id}")
//    public ResponseEntity<?> memberStatusDeActive(@PathVariable Long member_id) throws SystemError {
//        return new ResponseEntity<>(services.setStatusUserDeactive(member_id), HttpStatus.OK);
//    }
//
//
//    @DeleteMapping()
//    public ResponseEntity<?> deleteUsers(Principal principal) throws SystemError {
//        return new ResponseEntity<>(services.deleteUser(principal.getName()), HttpStatus.OK);
//    }
//

}
