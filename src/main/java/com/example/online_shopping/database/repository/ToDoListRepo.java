package com.example.online_shopping.database.repository;

import com.example.online_shopping.database.domain.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoListRepo extends JpaRepository<ToDoList, Long> {

    @Query(value = "select * from to_do_list where user_id=?1 order by id", nativeQuery = true)
    List<ToDoList> findAllByUserId(long user_id);

    @Query(value = "select * from to_do_list where user_id=?1 and id=?2", nativeQuery = true)
    ToDoList findByUserIdAndId(long user_id, long id);

    @Query(value = "delete from to_do_list where user_id=?1 and id=?2", nativeQuery = true)
    void deleteUserIdAndId(long user_id, long id);
}
