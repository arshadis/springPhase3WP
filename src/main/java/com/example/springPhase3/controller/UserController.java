package com.example.springPhase3.controller;

import com.example.springPhase3.dto.*;
import com.example.springPhase3.model.*;
import com.example.springPhase3.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Base64;
import java.util.List;


@RestController
// @RequestMapping("/api")

@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto) {
        System.out.println(userDto);

        if (userDto.getFirstname() == null||userDto.getFirstname().equals("")) {
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);

            Map<String, String> error = new HashMap<>();
            error.put("message", "نام نمی تواند خالی باشد");

            response.put("error", error);
            System.out.println(response);
            return ResponseEntity.ok(response);
        }
        if (userDto.getLastname() == null||userDto.getLastname().equals("")) {
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);

            Map<String, String> error = new HashMap<>();
            error.put("message", "نام خانوادگی نمی تواند خالی باشد");

            response.put("error", error);
            System.out.println(response);
            return ResponseEntity.ok(response);
        }
        if (userDto.getPassword() == null||userDto.getPassword().equals("")) {
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);

            Map<String, String> error = new HashMap<>();
            error.put("message", "پسورد نمی تواند خالی باشد");

            response.put("error", error);
            System.out.println(response);
            return ResponseEntity.ok(response);
        }
        if (userDto.getType() == null||userDto.getType().equals("")) {
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);

            Map<String, String> error = new HashMap<>();
            error.put("message", "نوع بازیکن نمی تواند خالی باشد");

            response.put("error", error);
            System.out.println(response);
            return ResponseEntity.ok(response);
        }
        if (userDto.getEmail() == null||userDto.getEmail().equals("")) {
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);

            Map<String, String> error = new HashMap<>();
            error.put("message", "ایمیل نمی تواند خالی باشد");

            response.put("error", error);
            System.out.println(response);
            return ResponseEntity.ok(response);
        }

        User existingUser = userService.findByEmail(userDto.getEmail());
        if (existingUser!=null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);

            Map<String, String> error = new HashMap<>();
            error.put("message", "قبلا با این ایمیل ثبت‌نام شده است");

            response.put("error", error);
            System.out.println(response);
            return ResponseEntity.ok(response);
        }

        User user = new User();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setType(userDto.getType());
        user.setScore(Long.valueOf(0));
        user.setToken(generateToken());
        userService.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);

        return ResponseEntity.ok(response); 
    }
    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16]; // 16 bytes = 128-bit token
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0,10);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        User user = userService.findByEmail(userDto.getEmail());
        
        if (user!=null && user.getPassword().equals(userDto.getPassword())) {
            Map<String, Object> response = new HashMap<>();

            response.put("success", true);
            Map<String, String> data = new HashMap<>();
            data.put("token", user.getToken());
            data.put("type", user.getType());
            response.put("data", data);

            return ResponseEntity.ok(response);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", false);

        Map<String, String> error = new HashMap<>();
        error.put("message", "ایمیل یا رمز عبور اشتباه است");

        response.put("error", error);
        System.out.println(response);
        return ResponseEntity.ok(response);        
    }

    @PostMapping("/validate_token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authorization) {
        String token = extractToken(authorization);
        User user = userService.findByToken(token);

        boolean valid=true;
        if (user==null) {
            valid=false;
        }
        int type = valid ? Integer.parseInt(user.getType()) : 0;

        Map<String, Object> response = new HashMap<>();

        response.put("success", true);
        Map<String, String> data = new HashMap<>();
        data.put("valid", String.valueOf(valid));
        data.put("type", String.valueOf(type));
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    private String extractToken(String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

@Autowired
private FollowerService followerService;

@Autowired
private CategoryService categoryService;

@PostMapping("/follow")
public ResponseEntity<?> follow(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, Long> body) {
    String token = extractToken(authorization);
    User user = userService.findByToken(token);

    if (user==null) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "توکن نامعتبر است");
        response.put("error", error);
        return ResponseEntity.ok(response); 
    }

    if (!"1".equals(user.getType())) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "شما دسترسی ندارید");
        response.put("error", error);
        System.out.println(response);
        return ResponseEntity.ok(response); 
    }

    Long userId = body.get("user_id");
    if (user.getId().equals(userId)) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message",  "شما نمی توانید خودتان را دنبال کنید");
        response.put("error", error);
        System.out.println(response);
        return ResponseEntity.ok(response); 
    }

    Follower existingFollower = followerService.findByFollowerAndFollowed(user.getId(), userId);

    if (existingFollower!=null) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message",   "شما قبلاً این کاربر را دنبال کرده‌اید" );
        response.put("error", error);
        System.out.println(response);
        return ResponseEntity.ok(response); 
    }

    Follower follower = new Follower();
    follower.setFollowerId(user.getId());
    follower.setFollowedId(userId);
    followerService.save(follower);

    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    System.out.println(response);
    return ResponseEntity.ok(response); }

@PostMapping("/unfollow")
public ResponseEntity<?> unfollow(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, Long> body) {
    String token = extractToken(authorization);
    User user = userService.findByToken(token);

    if (user==null) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "توکن نامعتبر است");
        response.put("error", error);
        return ResponseEntity.ok(response); 
    }

    Long userId = body.get("user_id");
    if (user.getId().equals(userId)) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "شما نمی توانید خودتان را از دنبال کردن حذف کنید");
        response.put("error", error);
        return ResponseEntity.ok(response); 
    }

    Follower existingFollower = followerService.findByFollowerAndFollowed(user.getId(), userId);
    if (existingFollower==null) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "شما این کاربر را دنبال نکرده‌اید" );
        response.put("error", error);
        return ResponseEntity.ok(response); 
    }

    followerService.delete(existingFollower);

    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    return ResponseEntity.ok(response);
}

@PostMapping("/new_category")
public ResponseEntity<?> createCategory(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, String> body) {
    String token = extractToken(authorization);
    System.out.println(3);
    System.out.println(body);

    User user = userService.findByToken(token);

    if (user==null) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "توکن نامعتبر است");
        response.put("error", error);
        return ResponseEntity.ok(response); 
    }

    if (!"2".equals(user.getType())) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "شما دسترسی ندارید");
        response.put("error", error);
        return ResponseEntity.ok(response); 
    }

    String category = body.get("category");
    if (category == null || category.isEmpty()) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "دسته‌بندی نمی‌تواند خالی باشد");
        response.put("error", error);
        return ResponseEntity.ok(response); 
    }

    boolean categoryExists = categoryService.existsByName(category);
    if (categoryExists) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "دسته‌بندی تکراری است");
        response.put("error", error);
        return ResponseEntity.ok(response); 
    }

    categoryService.save(new Category(category));
    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    return ResponseEntity.ok(response);
}

@Autowired
private QuestionService questionService;

@PostMapping("/designer_view")
public ResponseEntity<?> designerView(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, Long> body) {
    String token = extractToken(authorization);
    System.out.println(2);
    System.out.println(body);


    User user = userService.findByToken(token);

    if (user==null) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "توکن نامعتبر است");
        response.put("error", error);
        return ResponseEntity.ok(response); 
        }

    Long designerId = body.get("designer_id");
    if (designerId == null) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "شناسه طراح نمی‌تواند خالی باشد");
        response.put("error", error);
        return ResponseEntity.ok(response); 
    }

    User designer = userService.findByIdAndType(designerId, "2");
    if (designer==null) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "طراح یافت نشد");
        response.put("error", error);
        return ResponseEntity.ok(response); 
    }

    // List<QuestionStatistics> designedQuestions = questionService.getQuestionStatisticsByDesignerId(designerId);
    // TODO:

    List<QuestionStatistics> designedQuestions = null;
    boolean isFollowing = followerService.isFollowing(user.getId(), designerId);

    return ResponseEntity.ok(Map.of(
        "success", true,
        "data", Map.of(
            "firstname", designer.getFirstName(),
            "lastname", designer.getLastName(),
            "email", designer.getEmail(),
            "designedCount", designedQuestions.size(),
            "correctAnsweredCount", designedQuestions.stream().mapToLong(QuestionStatistics::getCorrectCount).sum(),
            "notCorrectAnsweredCount", designedQuestions.stream().mapToLong(QuestionStatistics::getNotCorrectCount).sum(),
            "isFollowing", isFollowing
        )
    ));
}
@Autowired
private AnswerService answerService;


@PostMapping("/player_view")
public ResponseEntity<?> playerView(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, Long> body) {
    String token = extractToken(authorization);
    User user = userService.findByToken(token);
    System.out.println(1);
    System.out.println(body);


    if (user==null) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "توکن نامعتبر است");
        response.put("error", error);
        return ResponseEntity.ok(response);
    }

    Long playerId = body.get("player_id");
    if (playerId == null) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "شناسه بازیکن نمی‌تواند خالی باشد");
        response.put("error", error);
        return ResponseEntity.ok(response);
    }

    User player = userService.findByIdAndType(playerId, "1");
    if (player==null) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "بازیکن یافت نشد");
        response.put("error", error);
        return ResponseEntity.ok(response);
    }

    AnswerStatistics answers = answerService.getAnswerStatisticsByUserId(playerId);
    boolean isFollowing = followerService.isFollowing(user.getId(), playerId);
    Map<String, Object> response = new HashMap<>();

    response.put("success", true);
    Map<String, String> data = new HashMap<>();
    data.put("firstname", player.getFirstName());
    data.put("lastname", player.getLastName());
    data.put("email", player.getEmail());
    data.put("playerScore", String.valueOf(player.getScore()));
    data.put("correctAnswerCount", String.valueOf(answers.getCorrectAnswers()));
    data.put("notCorrectAnswerCount",String.valueOf( answers.getNotCorrectAnswers()));
    data.put("isFollowing",String.valueOf(isFollowing));
    response.put("data", data);

    return ResponseEntity.ok(response);
}

@PostMapping("/new_designed_question")
public ResponseEntity<?> newDesignedQuestion(@RequestHeader("Authorization") String authorization, @RequestBody NewQuestionRequest request) {
    String token = extractToken(authorization);
    User user = userService.findByToken(token);

    if (user==null) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "توکن نامعتبر است");
        response.put("error", error);
        return ResponseEntity.ok(response);
    }

    if (!"2".equals(user.getType())) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "شما دسترسی ندارید");
        response.put("error", error);
        return ResponseEntity.ok(response);
    }

    if (!request.isValid()) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "ورودی‌های اجباری ناقص هستند");
        response.put("error", error);
        return ResponseEntity.ok(response);
    }

    Optional<Category> category = categoryService.findById(request.getCategory());
    if (category.isEmpty()) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> error = new HashMap<>();
        error.put("message", "دسته‌بندی یافت نشد");
        response.put("error", error);
        return ResponseEntity.ok(response);
    }
    String [] options={request.getFirstOption(),request.getSecondOption(),request.getThirdOption(),request.getFourthOption()};
    questionService.createQuestion(request.getQuestion(),options,(long)request.getCorrect(),Long.valueOf(request.getLevel()),request.getCategory(),user.getId());
    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    return ResponseEntity.ok(response);
}

}
