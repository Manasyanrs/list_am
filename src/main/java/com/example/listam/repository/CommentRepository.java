package com.example.listam.repository;

import com.example.listam.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("select c from Comment c where c.item.id= :itemId")
    List<Comment> getCommentsByItemId(@Param("itemId") int itemId);
}
