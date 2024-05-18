package com.example.bookingmovieticket.config;

import com.example.bookingmovieticket.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//@Component
//public class AuthenticationInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // Lấy thông tin user từ SecurityContextHolder
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            // Lấy thông tin User từ Authentication
//            User user = (User) authentication.getPrincipal();
//
//            // Lưu thông tin User vào request attribute để sử dụng ở bất kỳ trang web nào
//            request.setAttribute("currentUser", user);
//        }
//        return true;
//    }
//}
