package com.example.springPhase3.controller;

import com.example.springPhase3.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PlayerController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;
    
    @PostMapping("/score_player")
    public ResponseEntity<?> getPlayerScores(@RequestHeader("Authorization") String token) {
        System.out.println(10);

        if (!userService.isValidToken(token)) {

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "توکن نامعتبر است");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        Long userId = userService.getUserIdFromToken(token);
        if (!userService.isPlayer(userId)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "شما دسترسی ندارید");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        List<Map<String, Object>> scores = userService.getPlayerScores();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        Map<String, Object> data = new HashMap<>();
        data.put("table", scores);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/answered_question")
    public ResponseEntity<?> getAnsweredQuestions(@RequestHeader("Authorization") String token) {
        System.out.println(9);

        if (!userService.isValidToken(token)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "توکن نامعتبر است");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        Long userId = userService.getUserIdFromToken(token);
        if (!userService.isPlayer(userId)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "شما دسترسی ندارید");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        List<Map<String, Object>> answeredQuestions = questionService.getAnsweredQuestions(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        Map<String, Object> data = new HashMap<>();
        data.put("table", answeredQuestions);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get_not_answered_question")
    public ResponseEntity<?> getNotAnsweredQuestion(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> payload) {
                System.out.println(8);

        if (!userService.isValidToken(token)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "توکن نامعتبر است");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        Long userId = userService.getUserIdFromToken(token);
        if (!userService.isPlayer(userId)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "شما دسترسی ندارید");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        String type = (String) payload.get("type");
        if (type == null||type.equals("")) {
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);

            Map<String, String> error = new HashMap<>();
            error.put("message", "نوع سوال نمی تواند خالی باشد");

            response.put("error", error);
            System.out.println(response);
            return ResponseEntity.ok(response);
        }

        if (!type.equals("random") && !type.equals( "category")) {
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);

            Map<String, String> error = new HashMap<>();
            error.put("message", "نوع سوال نامعتبر است");

            response.put("error", error);
            System.out.println(response);
            return ResponseEntity.ok(response);
        }

        Long categoryId = (Long) payload.get("id");

        Map<String, Object> question = questionService.getNotAnsweredQuestion(userId, type, categoryId);
        if (question == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "سوالی یافت نشد");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", question);
        return ResponseEntity.ok(response);

        
        }

    @PostMapping("/check_question_answer")
    public ResponseEntity<?> checkQuestionAnswer(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> payload) {

        if (!userService.isValidToken(token)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "توکن نامعتبر است");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        Long userId = userService.getUserIdFromToken(token);
        if (!userService.isPlayer(userId)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message", "شما دسترسی ندارید");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }


        if (( payload.get("question_id"))==null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message","شناسه سوال نمی تواند خالی باشد");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }
        Long questionId = ((Number) payload.get("question_id")).longValue();

        int option = (int) payload.get("option");

        boolean alreadyAnswered = answerService.existsByUserIdAndQuestion_Id(userId, questionId);
        if (alreadyAnswered) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            Map<String, String> error = new HashMap<>();
            error.put("message","شما قبلاً به این سوال پاسخ داده‌اید");
            response.put("error", error);
            return ResponseEntity.ok(response);
        }

        boolean isCorrect = answerService.checkAnswer(userId, questionId, option);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        Map<String, Object> data = new HashMap<>();
        data.put("correct", isCorrect);
        response.put("data", data);
        return ResponseEntity.ok(response);        
    }
}
