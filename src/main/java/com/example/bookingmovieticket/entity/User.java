package com.example.bookingmovieticket.entity;

import com.example.bookingmovieticket.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.Date;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    @Column(nullable = false, unique = true)
    String email;

    String password;
    String phone;
    String avatar;

    @Enumerated(EnumType.STRING)
    UserRole role;

    Boolean enabled;

    Date createdAt;
    Date updatedAt;


}
