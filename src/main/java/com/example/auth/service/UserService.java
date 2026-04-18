package com.example.auth.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String register(User user) {
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            return "User already exists";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "Registered successfully";
    }

    public String login(String email, String password) {
        User user = repo.findByEmail(email).orElse(null);

        if (user == null) return "Invalid email";

        if (!encoder.matches(password, user.getPassword())) {
            return "Invalid password";
        }

        return "Login successful";
    }
}