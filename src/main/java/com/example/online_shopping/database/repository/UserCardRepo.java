package com.example.online_shopping.database.repository;

import com.example.online_shopping.database.domain.UserCard;
import com.example.online_shopping.database.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCardRepo extends JpaRepository<UserCard, Long> {
}
