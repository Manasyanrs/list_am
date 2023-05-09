package com.example.listam.controller;

import com.example.listam.entity.Category;
import com.example.listam.entity.Comment;
import com.example.listam.entity.Item;
import com.example.listam.repository.CategoryRepository;
import com.example.listam.repository.CommentRepository;
import com.example.listam.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("")
    public String getItemsPage(ModelMap modelMap) {
        List<Item> all = itemRepository.findAll();
        modelMap.addAttribute("items", all);
        return "items";
    }

    @GetMapping("/{id}")
    public String getItem(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Item> byId = itemRepository.findById(id);
        List<Comment> commentsByItemId = commentRepository.getCommentsByItemId(id);
        if (byId.isPresent()) {
            Item item = byId.get();
            modelMap.addAttribute("item", item);
            modelMap.addAttribute("comments", commentsByItemId);
            return "getItem";

        } else {
            return "redirect:/items";
        }
    }

    @GetMapping("/add")
    public String addItemPage(ModelMap modelMap) {
        List<Category> all = categoryRepository.findAll();
        modelMap.addAttribute("categories", all);
        return "addItem";

    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/items";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam("id") int itemId) {
        List<Comment> commentsByItemId = commentRepository.getCommentsByItemId(itemId);
        if (!commentsByItemId.isEmpty()) {
            for (Comment comment : commentsByItemId) {
                commentRepository.delete(comment);
            }
        }
        itemRepository.deleteById(itemId);
        return "redirect:/items";
    }
}
