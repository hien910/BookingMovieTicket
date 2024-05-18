package com.example.bookingmovieticket.entity;

import com.example.bookingmovieticket.model.enums.StatusTicket;
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
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Double totalPrice;

    Date bookingTime;

    @Enumerated(EnumType.STRING)
    StatusTicket status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "show_time_id")
    ShowTime showTime;

    Date updatedAt;
}
