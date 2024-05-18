package com.example.bookingmovieticket.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertUserRequest {
    String name;
    String phone;
    String avatar;
    Date birthday;
    String email;
}

