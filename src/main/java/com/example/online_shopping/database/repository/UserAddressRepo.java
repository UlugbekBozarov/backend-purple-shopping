package com.example.online_shopping.database.repository;

import com.example.online_shopping.database.domain.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepo extends JpaRepository<UserAddress, Long> {
}