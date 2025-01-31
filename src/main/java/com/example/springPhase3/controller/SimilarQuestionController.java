package com.example.springPhase3.controller;

import com.example.springPhase3.model.*;
import com.example.springPhase3.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SimilarQuestionController {

    private final SimilarQuestionService similarQuestionService;

    @Autowired
    private UserService userService;

    @Autowired
    public SimilarQuestionController(SimilarQuestionService similarQuestionService) {
        this.similarQuestionService = similarQuestionService;
    }

    @PostMapping("/set_similar_question")
    public ResponseEntity<?> setSimilarQuestion(
            @RequestBody Map<String, Object> requestBody,
            @RequestHeader("Authorization") String token) {
                System.out.println(4);

        
        // Validate the user (implement validateToken logic)
        if (!userService.isValidToken(token)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message","توکن نامعتبر است");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        User user = userService.findByToken(token);
        if (user == null || user.getType().equals("2")) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message","شما دسترسی ندارید");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }
        
        Long questionId = ((Number) requestBody.get("question_id")).longValue();
        List<Long> similarQuestionIds = ((List<Number>) requestBody.get("similar_question_ids"))
                .stream()
                .map(Number::longValue)
                .toList();

        if (similarQuestionIds == null || similarQuestionIds.size() ==0) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message","لیست سوالات مشابه باید شامل حداقل یک سوال باشد");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }
        if (!similarQuestionService.validateQuestionExists(questionId)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message","سوال اصلی یافت نشد");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        if (similarQuestionIds.contains(questionId)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "سوال اصلی نمی تواند در سوالات مشابه باشد");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        similarQuestionService.setSimilarQuestions(questionId, similarQuestionIds);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);  
    }



    @PostMapping("/get_similar_question")
    public ResponseEntity<?> getSimilarQuestion(
            @RequestBody Map<String, Object> requestBody,
            @RequestHeader("Authorization") String token) {

        // Validate the user (implement validateToken logic)

         if (!userService.isValidToken(token)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message","توکن نامعتبر است");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        User user = userService.findByToken(token);

        if (user == null || user.getType().equals("2")) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message","شما دسترسی ندارید");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        Long questionId = ((Number) requestBody.get("question_id")).longValue();

        if (!similarQuestionService.validateQuestionExists(questionId)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message","سوال یافت نشد");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        List<Long> similarQuestions = similarQuestionService.getSimilarQuestions(questionId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        Map<String, Object> data = new HashMap<>();
        data.put("table", similarQuestions);
        response.put("data", data);
        return ResponseEntity.ok(response);  
    }
}
