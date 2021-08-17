package com.example.online_shopping.database.repository;

import com.example.online_shopping.database.domain.Vaucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VaucherRepo extends JpaRepository<Vaucher, Long> {

    @Query(value = "select * from vaucher where vaucher_code like ?1", nativeQuery = true)
    Vaucher findByVaucher_code(String vaucher_code);
}
