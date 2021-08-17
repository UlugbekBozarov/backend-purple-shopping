package com.example.online_shopping.database.repository;

import com.example.online_shopping.database.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepo extends JpaRepository<Comments, Long> {

    /*@Query(value = "select * from comments where user_id=?1 order by send_date desc", nativeQuery = true)
    List<Comments> findAllUser(Long user_id);*/


    @Query(value = "select * from comments where comment_id=?1 and user_id=?2", nativeQuery = true)
    Comments findByCommentIdAndUserId(Long comment_id, Long user_id);

    @Query(value = "select * from comments where type_id=?1 order by send_date", nativeQuery = true)
    List<Comments> findAllByTypeID(Long comment_id);
}
