package com.example.online_shopping.database.repository;

import com.example.online_shopping.database.domain.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItems, Long> {
}
