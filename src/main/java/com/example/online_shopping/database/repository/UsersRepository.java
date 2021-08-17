package com.example.online_shopping.database.repository;

import com.example.online_shopping.database.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);
    Users findByEmail(String email);
    List<Users> findAllByRole(String role);

//    Users findByUsername(String username);
//
//    @Query(value = "select * from users u where u.phone=?1", nativeQuery = true)
//    Users findByPhone(String phone);
//
//    @Query(value = "select * from users u where u.role like ('USER')", nativeQuery = true)
//    List<Users> findByNotUsername();

}
