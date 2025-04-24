package jpaprojects.foodorderingsystem.controller;

import jpaprojects.foodorderingsystem.config.JwtUtil;
import jpaprojects.foodorderingsystem.entity.User;
import jpaprojects.foodorderingsystem.repository.UserRepository;
import jpaprojects.foodorderingsystem.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Bu email ilə istifadəçi tapılmadı.");
        }

        String token = jwtUtil.generateTokenWithEmailOnly(email);
        String resetUrl = "http://localhost:8080/reset-password?token=" + token;
        String subject = "Şifrə sıfırlama";
        String content = "<p>Şifrənizi sıfırlamaq üçün bu linkə klikləyin:</p>" +
                "<p><a href=\"" + resetUrl + "\">Şifrəni sıfırla</a></p>";

        emailService.sendEmail(email, subject, content);
        return ResponseEntity.ok("Sıfırlama linki email ünvanınıza göndərildi.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token,
                                                @RequestParam String newPassword) {
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.badRequest().body("Token etibarsızdır.");
        }

        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Şifrə uğurla dəyişdirildi.");
    }
}
