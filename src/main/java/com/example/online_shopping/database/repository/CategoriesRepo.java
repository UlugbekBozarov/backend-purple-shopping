package com.example.online_shopping.database.repository;

import com.example.online_shopping.database.domain.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepo extends JpaRepository<Categories, Long> {

    @Query(value = "select * from categories order by id desc", nativeQuery = true)
    List<Categories> findAll();

    @Query(value = "select * from categories order by id asc", nativeQuery = true)
    List<Categories> findAllAsc();
}
