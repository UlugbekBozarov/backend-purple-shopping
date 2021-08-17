package com.example.online_shopping.database.domain.dbinit;

import com.example.online_shopping.database.domain.Users;
import com.example.online_shopping.database.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbInit implements CommandLineRunner {

    private UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DbInit(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {

        /*System.out.println("::::::::::::::::::::::");
        Users admin = new Users("999999999", "admin", passwordEncoder.encode("admin123"), "ADMIN");
        System.out.println(admin.toString());
        try {
            usersRepository.save(admin);
        } catch (Exception e) {
            System.out.println("Exception Create ADMIN::: " + e);
        }
        System.out.println(usersRepository.findByUsername("admin").toString());*/

    }
}
