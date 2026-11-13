package com.example.crudrapido.controller;
 
import java.util.Map;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.example.crudrapido.dto.AuthRequest;
import com.example.crudrapido.entity.AppUser;
import com.example.crudrapido.repository.UserRepository;
import com.example.crudrapido.security.JwtService;
 
import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/api/auth")
public class AuthController {
 
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Autowired
    private JwtService jwtService;
 
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El email ya está registrado"));
        }
        AppUser user = new AppUser();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
 
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("token", jwtService.generateToken(user.getEmail())));
    }
 
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        Optional<AppUser> user = userRepository.findByEmail(request.email());
 
        if (user.isEmpty() || !passwordEncoder.matches(request.password(), user.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email o contraseña incorrectos"));
        }
        return ResponseEntity.ok(Map.of("token", jwtService.generateToken(request.email())));
    }
}