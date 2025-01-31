package com.example.springPhase3.controller;

import com.example.springPhase3.service.QuestionService;
import com.example.springPhase3.service.CategoryService;
import com.example.springPhase3.service.UserService;
import com.example.springPhase3.dto.QuestionResponseDTO;
import com.example.springPhase3.dto.CategoryResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping("/get_designed_question")
    public ResponseEntity<?> getDesignedQuestions(@RequestHeader("Authorization") String token) {
        System.out.println(6);

        if (!userService.isValidToken(token)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "توکن نامعتبر است");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        Long userId = userService.getUserIdFromToken(token);
        if (!userService.isDesigner(userId)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "شما دسترسی ندارید");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        List<QuestionResponseDTO> questions = questionService.getDesignedQuestions(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        Map<String, Object> data = new HashMap<>();
        data.put("table", questions);
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/get_all_question")
    public ResponseEntity<?> getAllQuestions(@RequestHeader("Authorization") String token) {
        System.out.println(5);

        if (!userService.isValidToken(token)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "توکن نامعتبر است");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        Long userId = userService.getUserIdFromToken(token);
        if (!userService.isDesigner(userId)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "شما دسترسی ندارید");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        List<QuestionResponseDTO> questions = questionService.getAllQuestions();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        Map<String, Object> data = new HashMap<>();
        data.put("table", questions);
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/get_categories")
    public ResponseEntity<?> getCategories(@RequestHeader("Authorization") String token) {
        if (!userService.isValidToken(token)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "توکن نامعتبر است");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        Map<String, Object> data = new HashMap<>();
        data.put("table", categories);
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    
}
