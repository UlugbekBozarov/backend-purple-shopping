package com.example.online_shopping.database.repository;

import com.example.online_shopping.database.domain.Orders;
import com.example.online_shopping.database.domain.Users;
import com.example.online_shopping.database.domain.enumeration.ORDER_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long> {

    @Query(value = "select * from orders where user_id=?1 and status=1 or user_id=?1 and status=2 order by time_of_ordening desc ", nativeQuery = true)
    List<Orders> findAllByUserAndStatus(Users user);

    @Query(value = "select * from orders where status=1 order by time_of_ordening desc", nativeQuery = true)
    List<Orders> findAllByStatusAccepted();

    @Query(value = "select * from orders where status=1 or status=2 order by time_of_ordening desc", nativeQuery = true)
    List<Orders> findAllByStatusAcceptedOrStatusDelivery();

    @Query(value = "select * from orders where status=2 order by time_of_ordening desc", nativeQuery = true)
    List<Orders> findAllByStatusDelivery();

    @Query(value = "select * from orders where status=3 order by time_of_ordening desc limit 10", nativeQuery = true)
    List<Orders> findAllByStatusReceive();

    @Query(value = "select * from orders where status=2 and order_id=?1 and user_id=?2", nativeQuery = true)
    Orders findByOrder_idAndUser(Long order_id, Long user_id);

//
//    @Query(value = "select * from orders where user_id=?1 and product_id=?2", nativeQuery = true)
//    Orders findByUserIdAndProductId(Long user_id, Long product_id);
//
//    @Query(value = "select * from orders where user=?1 and order_id=?2", nativeQuery = true)
//    Orders findByMember_idAndOrder_id(Long user_id, Long order_id);
//
//    @Query(value = "delete from orders where user_id=?1", nativeQuery = true)
//    void deleteAllByMember_id(Long user_id);

}
