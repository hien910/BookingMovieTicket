package com.example.bookingmovieticket.service;

import com.example.bookingmovieticket.entity.TokenConfirm;
import com.example.bookingmovieticket.entity.User;
import com.example.bookingmovieticket.exception.BadRequestException;
import com.example.bookingmovieticket.model.enums.TokenType;
import com.example.bookingmovieticket.model.enums.UserRole;
import com.example.bookingmovieticket.model.request.*;
import com.example.bookingmovieticket.model.response.VerifyAccountResponse;
import com.example.bookingmovieticket.repository.TokenConfirmRepository;
import com.example.bookingmovieticket.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenConfirmRepository tokenConfirmRepository;
    private final UserRepository userRepository;
    private final  HttpSession session;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    public void login(LoginRequest request) {
        // Tạo đối tượng xác thực
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        try {
            // Tiến hành xác thực
            Authentication authentication = authenticationManager.authenticate(token);

            // Lưu đối tượng đã xác thực vào trong SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Lưu vào trong session
            session.setAttribute("MY_SESSION", authentication.getName()); // Lưu email -> session
        } catch (DisabledException e) {
            throw new RuntimeException("Tài khoản chưa được xác thực");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Email hoặc mật khẩu không đúng");
        }
    }

    @Transactional
    public String register(RegisterRequest request) throws MessagingException, UnsupportedEncodingException {
        // Kiểm tra email đã tồn tại chưa
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(new Date())
                .updatedAt(new Date())
                .role(UserRole.USER) // Sử dụng enum UserRole trực tiếp, mặc định là USER
                .enabled(false)
                .build();
        userRepository.save(user);

        // Lưu token vào database
        TokenConfirm tokenConfirm = TokenConfirm.builder()
                .token(UUID.randomUUID().toString())
                .tokenType(TokenType.REGISTRATION)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();
        tokenConfirmRepository.save(tokenConfirm);

        // Trả về path xác thực tài khoản
        // TODO: Link này gửi vào trong email
        String link = "http://localhost:8800/xac-thuc-tai-khoan?token=" + tokenConfirm.getToken();
        mailService.sendVerificationEmail(user, link);
        return "Đăng ký thành công, vui lòng kiểm tra email để xác thực tài khoản";
    }
    public void forgotPassword(InputEmailRequest email) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(email.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("Không tìm thấy user nào có email là: "+email));

        TokenConfirm tokenConfirm = TokenConfirm.builder()
                .token(UUID.randomUUID().toString())
                .tokenType(TokenType.FORGOT_PASSWORD)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();
        tokenConfirmRepository.save(tokenConfirm);

        // TODO: Link này gửi vào trong email
        String link = "http://localhost:8800/quen-mat-khau?token=" + tokenConfirm.getToken();
        mailService.sendPasswordRetrievalEmail(user, link);

    }
    public String verifyPasswordRetrieval(String token) {
        System.out.println(token);
        System.out.println("đây token đây!");
        Optional<TokenConfirm> optionalTokenConfirm = tokenConfirmRepository
                .findByTokenAndTokenType(token, TokenType.FORGOT_PASSWORD);
        System.out.println(optionalTokenConfirm);
        System.out.println("hahaha");
        if (optionalTokenConfirm.isEmpty()){
            return "Link lấy lại mật khẩu không tồn tại";
        }
        TokenConfirm tokenConfirm = optionalTokenConfirm.get();
        if (tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())){
            return "Link lấy lại mật khẩu đã hết hạn";
        }
        if (tokenConfirm.getConfirmedAt()!=null){
            return "Link lấy lại mật khẩu đã được sử dụng trước đó";
        }
//        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        tokenConfirmRepository.save(tokenConfirm);
        return tokenConfirm.getToken();
    }

    @Transactional
    public VerifyAccountResponse verifyAccount(String token) {
        // Tìm kiếm token trong database
        Optional<TokenConfirm> optionalTokenConfirm = tokenConfirmRepository
                .findByTokenAndTokenType(token, TokenType.REGISTRATION);

        // Kiểm tra token có tồn tại không
        if(optionalTokenConfirm.isEmpty()) {
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Token không hợp lệ")
                    .build();
        }

        // Kiểm tra xem token đã được xác thực chưa
        TokenConfirm tokenConfirm = optionalTokenConfirm.get();
        if(tokenConfirm.getConfirmedAt() != null) {
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Token đã được xác thực")
                    .build();
        }

        // Kiểm tra xem token đã hết hạn chưa
        if(tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())) {
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Token đã hết hạn")
                    .build();
        }

        // Xác thực tài khoản của user
        User user = tokenConfirm.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        // Cập nhật thời gian xác thực token
        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        tokenConfirmRepository.save(tokenConfirm);

        return VerifyAccountResponse.builder()
                .success(true)
                .message("Xác thực tài khoản thành công")
                .build();
    }

    public void changePassword(Integer id, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user") );


        // kiểm tra xem mật khẩu có đúng với mật khẩu được lưu trong db không
        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
        }else {
            throw new BadRequestException("Mật khẩu không chính xác");

        }

    }
    public void logout() {
        session.setAttribute("MY_SESSION",null);
        session.invalidate();
        SecurityContextHolder.clearContext();
    }

    public void updateUser(Integer id, UpsertUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->new UsernameNotFoundException("Không tìm thấy User"));

        user.setAvatar(request.getAvatar());
        user.setBirthday(request.getBirthday());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setUpdatedAt(new Date());

        userRepository.save(user);
    }

    public User updatePasswordRetrieval(UpsertPasswordRetrieval upsertPasswordRetrieval) {
        TokenConfirm tokenConfirm = tokenConfirmRepository
                .findByTokenAndTokenType(upsertPasswordRetrieval.getNewToken(), TokenType.FORGOT_PASSWORD).orElseThrow();
        tokenConfirm.getUser().setPassword(passwordEncoder.encode(upsertPasswordRetrieval.getPassword()));
        tokenConfirm.setConfirmedAt(LocalDateTime.now());
//        userRepository.save(tokenConfirm.getUser());
        return  userRepository.save(tokenConfirm.getUser());
    }
}
