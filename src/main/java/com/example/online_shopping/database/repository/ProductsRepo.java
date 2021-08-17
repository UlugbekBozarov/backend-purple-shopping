package com.example.online_shopping.database.repository;

import com.example.online_shopping.database.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long> {

    @Query(value = "select * from products order by category_id asc, product_id desc", nativeQuery = true)
    List<Products> findAll();

    @Query(value = "select * from products where category_id=?1 order by product_id", nativeQuery = true)
    List<Products> findAllByCategoryId(Long category_id);
}
