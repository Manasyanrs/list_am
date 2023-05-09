package com.example.listam.repository;

import com.example.listam.entity.Comment;
import com.example.listam.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query("select item from Item item where item.category.id= :categoryId")
    List<Item> getItemsByCategoryId(@Param("categoryId") int categoryId);
}
