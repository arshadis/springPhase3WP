package com.example.springPhase3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springPhase3.repository.*;
import com.example.springPhase3.model.*;
import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByIdAndType(Long id, String type) {
        return userRepository.findByIdAndType(id, type);
    }
 
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }

    public User save(User user) {
        return userRepository.save(user);  // This calls the save method provided by JpaRepository
    }

    public boolean isValidToken(String token) {
        // Logic to validate the token (e.g., using JWT or session-based validation)
        return token != null && !token.isEmpty(); // Placeholder implementation
    }

    public Long getUserIdFromToken(String token) {
        token=token.substring(7,17);
        System.out.println(token);
        // Extract user ID from token. For example, if you're using JWT, decode the token.
        // Placeholder: Assume the token contains the user ID directly.
        System.out.println("ke");
        System.out.println(userRepository.findByToken(token));
        System.out.println("kel");
        System.out.println(userRepository.findByToken(token));
        System.out.println("kell");
        // System.out.println(userRepository.findByToken(token).getId());
        System.out.println("kelll");
        try {
            User user=userRepository.findByToken(token);
            return user.getId();
            // return Long.parseLong(token); // Replace this with actual token decoding
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public boolean isDesigner(Long userId) {
        // Check if the user is a designer based on their role.
        return userRepository.findById(userId)
            .map((User user) -> user.getType() == "2") // Explicitly specify `User`
            .orElse(false);

    }

    public boolean isPlayer(Long userId) {
        System.out.println("sobhan");
        System.out.println(userRepository.findById(userId).get().getType());


        return userRepository.findById(userId)
                .map(user -> user.getType() == "1")
                .orElse(false);
    }

public List<Map<String, Object>> getPlayerScores() {
    System.out.println("in getPlayerScores:");
    return userRepository.findAllByTypeOrderByScoreDesc("1").stream()
            .map(user -> {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", user.getId());
                userMap.put("name", user.getFirstName() + " " + user.getLastName());
                userMap.put("score", user.getScore());
                return userMap;
            })
            .toList();
}

}
