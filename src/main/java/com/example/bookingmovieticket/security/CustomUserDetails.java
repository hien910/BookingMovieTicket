package com.example.bookingmovieticket.security;


import com.example.bookingmovieticket.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomUserDetails implements UserDetails {
    User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(user.getRole())
                .map(role -> new SimpleGrantedAuthority("ROLE_"+ role.getValue()))
                .toList();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public String getEmail() {
        return this.user.getEmail();
    }
    public String getAvatar(){
        return this.user.getAvatar();
    }

    public String getName(){
        return this.user.getName();
    }
    public String getPhone(){
        return this.user.getPhone();
    }
    public Date getBirthDay(){
        return this.user.getBirthday();
    }

    public Integer getId(){
        return this.user.getId();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
