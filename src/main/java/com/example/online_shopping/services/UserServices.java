package com.example.online_shopping.services;

import com.example.online_shopping.database.domain.UserAddress;
import com.example.online_shopping.database.domain.UserCard;
import com.example.online_shopping.database.domain.UserContact;
import com.example.online_shopping.database.domain.Users;
import com.example.online_shopping.database.domain.enumeration.GENDER;
import com.example.online_shopping.database.repository.*;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.erroe.SystemError;
import com.example.online_shopping.erroe.message.ErrorMessage;
import com.example.online_shopping.request.user.*;
import com.example.online_shopping.response.message.Message;
import com.example.online_shopping.security.LoginViewModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

import static com.example.online_shopping.database.domain.enumeration.STATUS.ACTIVE;
import static com.example.online_shopping.database.domain.enumeration.STATUS.DEACTIVE;

@Slf4j
@Service
public class UserServices {

    private UsersRepository usersRepository;

    private CommentsRepo commentsRepo;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServices(UsersRepository usersRepository, CommentsRepo commentsRepo, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.commentsRepo = commentsRepo;
        this.passwordEncoder = passwordEncoder;
    }


//    S i g n - U p
    public Message signUpUser(SignUp u) throws NotFound {

        ErrorMessage error = new ErrorMessage(-112, "Could not save to database. Please try again later.");

        try {
            if (checkUsername(u.getUsername())) {
                if (checkEmail(u.getEmail())) {
                    Users user = new Users(u.getUsername(), u.getEmail(), passwordEncoder.encode(u.getPassword()), "USER");
                    usersRepository.save(user);
                    log.info("Create user : {}", user.toString());
                    Message message = new Message(201, "You are registered");
                    return message;
                } else {
                    error = new ErrorMessage(-301, "Invalid email");
                    throw new NotFound("sign-up user", -1, error);
                }
            } else {
                error = new ErrorMessage(-301, "Invalid username");
                throw new NotFound("sign-up user", -1, error);
            }
        } catch (Exception e) {
            throw new NotFound("sign-up user", -2, error);
        }
    }


//    G e t   M e
    public Users getMe(String username) throws SystemError {
        try {
            return usersRepository.findByUsername(username);
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(99, "Could not retrieve user data from the database!");
            throw new SystemError("getMember", -1, error);
        }
    }


    public Users updateUser(UpdateUser u, String username) {

        try {

            Users user = usersRepository.findByUsername(username);

            user.setFullname(u.getFullname());
            user.setGender(u.getGender());
            user.setBirthday(u.getBirthday());
            user.setUser_image(u.getImage());

            usersRepository.save(user);

            log.info("Update user : {}", user.toString());

            return user;
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(-112, "Could not save to database. Please try again later.");
            throw new SystemError("login", -2, error);
        }
    }


    public String updatePassword(String username, String password) {
        try {
            Users user = usersRepository.findByUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            log.info("Update Password: {}", user.toString());
            return "Update Password";
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(99, "Could not retrieve user data from the database!");
            throw new SystemError("update password", -1, error);
        }
    }

    public String updatePasswordRoleAdmin(String username, LoginViewModel userView) {
        try {
            Users admin = usersRepository.findByUsername(username);
            if (admin.getRole().equals("ADMIN")) {
                Users user = usersRepository.findByUsername(userView.getUsername());
                if (user.getRole().equals("USER")) {
                    user.setPassword(passwordEncoder.encode(userView.getPassword()));
                    log.info("Update Admin Password: {}", user.toString());
                    return "Update User Password";
                } else {
                    return "Not Update Password";
                }
            } else {
                return "Sorry!";
            }
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(99, "Could not retrieve user data from the database!");
            throw new SystemError("update password", -1, error);
        }
    }


    public List<Users> getUsers() throws SystemError {

        try {
            return usersRepository.findAllByRole("USER");
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(99, "Could not retrieve user data from the database!");
            throw new SystemError("getMember", -1, error);
        }
    }




    public String createExpert() {
        try {
            if (usersRepository.findAll().size() == 0) {
                Users user = new Users("Ulug'bek Bozarov Toxirjon og'li", "777777777", passwordEncoder.encode("project-2, created-date: (01.05.2021), Online-Shopping, brithdate(08.01.1999)"), "EXPERT");
                user.setFullname("Bozarov Ulug'bek Toxirjon o'g'li");
                user.setGender(GENDER.MALE);
                log.info("Create Project Expert: {}", user.toString());

                usersRepository.save(user);
                return "Create Expert";
            } else {
                return "Created Expert";
            }

        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(99, "Could not retrieve user data from the database!");
            throw new SystemError("create expert", -1, error);
        }
    }

    public String createAdmin(String username, String email, String password) {
        try {
            Users user = new Users(username, email, passwordEncoder.encode(password), "ADMIN");
            log.info("Create Project Admin: {}", user.toString());

            usersRepository.save(user);
            return "Create Admin";
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(99, "Could not retrieve user data from the database!");
            throw new SystemError("create admin", -1, error);
        }
    }

    public String updateAdminPassword(String username, String password) {
        try {
            Users user = usersRepository.findByUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            log.info("Update Admin Password: {}", user.toString());

            return "Update Password";
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(99, "Could not retrieve user data from the database!");
            throw new SystemError("update admin password", -1, error);
        }

    }








//    public List<UserComment> getUsersComment() throws SystemError {
//        try {
//            List<Users> l = usersRepository.findByNotUsername();
//            List<UserComment> userComments = new ArrayList<>();
//            for (int i = 0; i < l.size(); i++) {
//                Users user = l.get(i);
//                String message = "";
//                String role = "";
//                int size = 0;
//                Date d = null;
//                Date s = null;
//                COMMENT_STATUS status = null;
//                try {
//                    List<Comments> comments = commentsRepo.findAllByTypeID(l.get(i).getUser_id());
//                    int length = comments.size();
//                    message = comments.get(length - 1).getMessage();
//                    role = comments.get(length - 1).getRole();
//                    status = comments.get(length - 1).getStatus();
//                    boolean bool = true;
//                    for (int j = 0; j < comments.size(); j++) {
//                        if (comments.get(j).getStatus() == SEND) {
//                            size++;
//                        }
//
//                        if (comments.get(comments.size() - (j + 1)).getRole().equals("USER") && bool) {
//                            s = comments.get(comments.size() - (j + 1)).getSend_date();
//                            bool = false;
//                        }
//                    }
//                    d = comments.get(comments.size() - 1).getSend_date();
//                } catch (Exception e) {
//                    message = "";
//                }
//
//                if (!message.equals("")) {
//                    UserComment u = new UserComment(user.getUser_id(), user.getFullname(), user.getUsername(), role, user.getUser_image(), message, size, d, s, status);
//                    userComments.add(u);
//                }
//            }
//            return userComments;
//        } catch (Exception e) {
//            System.out.println("E: " + e);
//            ErrorMessage error = new ErrorMessage(99, "Could not retrieve user data from the database!");
//            throw new SystemError("getMember", -1, error);
//        }
//    }


//    public Users getUserByUsername(String username) throws SystemError {
//
//        try {
//            return usersRepository.findByUsername(username);
//        } catch (Exception e) {
//            ErrorMessage error = new ErrorMessage(99, "Could not retrieve user data from the database!");
//            throw new SystemError("getMember", -1, error);
//        }
//    }


//    public Message signUpAdmin(SignUp admin) {
//
//        try {
//
//            Users user = new Users(admin.getEmail(),
//                    admin.getUsername(),
//                    passwordEncoder.encode(admin.getPassword()),
//                    "ADMIN");
//
//            usersRepository.save(user);
//
//
//            Message message = new Message(201, "Create Admin!");
//
//            log.info("Create admin : {}", user.toString());
//            return message;
//        } catch (Exception e) {
//            ErrorMessage error = new ErrorMessage(-112, "Could not save to database. Please try again later.");
//            throw new SystemError("login", -2, error);
//        }
//    }




//    public boolean checkUserPhone(String phone) {
//        try {
//            Users user = usersRepository.findByPhone(phone);
//            if (user == null) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            return true;
//        }
//    }

    public boolean checkUsername(String username) {
        try {
            Users user = usersRepository.findByUsername(username);
            if (user == null) {
                return true;
            } else return false;
        } catch (Exception e) {
            return true;
        }
    }

    public boolean checkEmail(String email) {
        try {
            Users user = usersRepository.findByEmail(email);
            if (user == null) {
                return true;
            } else return false;
        } catch (Exception e) {
            return true;
        }
    }





    public Message confirmPassword(SignUp password, String username) {

        try {

            Users user = usersRepository.findByUsername(username);

            user.setPassword(passwordEncoder.encode(password.getPassword()));

            usersRepository.save(user);

            log.info("Confirm Password : {}", user.toString());

            Message message = new Message(200, "The Password Changed!");
            return message;
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(-112, "Could not save to database. Please try again later.");
            throw new SystemError("login", -2, error);
        }
    }


    public Message setStatusUserActive(Long user_id) {

        try {

            Users user = usersRepository.findById(user_id).get();
            user.setStatus(ACTIVE);
            usersRepository.save(user);

            log.info("Member Set Status Active : {}", user.toString());

            Message message = new Message(204, user.getUsername() + " activated");
            return message;
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(99, "Could not retrieve user data from the database!");
            throw new SystemError("set status user active", -1, error);
        }
    }


    public Message setStatusUserDeactive(Long user_id) {

        try {

            Users user = usersRepository.findById(user_id).get();
            user.setStatus(DEACTIVE);
            usersRepository.save(user);

            log.info("Member Set Status Deactive : {}", user.toString());

            Message message = new Message(205, user.getUsername() + " deactivated");
            return message;

        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(99, "Could not retrieve user data from the database!");
            throw new SystemError("set status user deactive", -1, error);
        }
    }


    public Message deleteUser(String username) {

        try {
            Users user = usersRepository.findByUsername(username);
            usersRepository.deleteById(user.getUser_id());

            log.info("Delete User : {}", user.toString());

            Message message = new Message(203, "The user has been deleted!");
            return message;

        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(99, "Could not retrieve user data from the database!");
            throw new SystemError("delete user", -1, error);
        }
    }



}
