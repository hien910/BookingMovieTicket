package com.example.bookingmovieticket.rest;

import com.example.bookingmovieticket.entity.User;
import com.example.bookingmovieticket.model.request.*;
import com.example.bookingmovieticket.security.CustomUserDetails;
import com.example.bookingmovieticket.service.AuthService;
import com.example.bookingmovieticket.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        authService.login(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(authService.register(request));
    }
    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UpsertUserRequest request, @PathVariable Integer id){
        authService.updateUser(id,request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody InputEmailRequest email) throws MessagingException, UnsupportedEncodingException {
        authService.forgotPassword(email);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/password-retrieval")
    public ResponseEntity<?> changePassword(@RequestBody UpsertPasswordRetrieval upsertPasswordRetrieval){
        System.out.println(upsertPasswordRetrieval);
        return ResponseEntity.ok(authService.updatePasswordRetrieval(upsertPasswordRetrieval));
    }

    @PutMapping ("/change-password/{id}")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,@PathVariable Integer id) {
        authService.changePassword(id, changePasswordRequest);
        return ResponseEntity.ok().build();
    }

}
