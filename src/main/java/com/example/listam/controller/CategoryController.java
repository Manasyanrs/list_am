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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CommentRepository commentRepository;

    @GetMapping("")
    public String categoriesPage(ModelMap modelMap) {
        List<Category> all = categoryRepository.findAll();
        modelMap.addAttribute("categories", all);
        return "categories";
    }

    @PostMapping("")
    public String addCategory(@RequestParam("categoryName") String categoryName) {
        categoryRepository.save(
                Category.builder()
                        .name(categoryName)
                        .build()
        );
        return "redirect:/categories";
    }

    @PostMapping("/remove")
    public String deleteCategory(@RequestParam("id") int categoryId) {
        List<Item> itemsByCategoryId = itemRepository.getItemsByCategoryId(categoryId);

        if (!itemsByCategoryId.isEmpty()) {
            for (Item item : itemsByCategoryId) {
                List<Comment> commentsByItemId = commentRepository.getCommentsByItemId(item.getId());
                if (!commentsByItemId.isEmpty()) {
                    for (Comment comment : commentsByItemId) {
                        commentRepository.delete(comment);
                    }
                }
                itemRepository.deleteById(item.getId());
            }
        }
        categoryRepository.deleteById(categoryId);
        return "redirect:/categories";
    }
}
