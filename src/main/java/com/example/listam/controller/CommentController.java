package com.example.listam.controller;

import com.example.listam.entity.Comment;
import com.example.listam.entity.Item;
import com.example.listam.repository.CommentRepository;
import com.example.listam.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ItemRepository itemRepository;


    @PostMapping("/items/{id}")
    public String addComment(@PathVariable("id") int id,
                             @RequestParam("addComment") String comment) {
        Optional<Item> byId = itemRepository.findById(id);
        commentRepository.save(Comment.builder()
                .comment(comment)
                .item(byId.get())
                .build());
        return "redirect:/items/" + id;
    }

    @PostMapping("/comment/remove")
    public String deleteComment(@RequestParam("commentId") int commentId) {
        Optional<Comment> c = commentRepository.findById(commentId);
        int id = c.get().getItem().getId();
        commentRepository.deleteById(commentId);
        return "redirect:/items/" + id;
    }


}
