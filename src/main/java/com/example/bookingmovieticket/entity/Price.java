package com.example.bookingmovieticket.entity;

import com.example.bookingmovieticket.model.enums.DayType;
import com.example.bookingmovieticket.model.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Double Price;

    @Enumerated(EnumType.STRING)
    SeatType seatType;

    @Enumerated(EnumType.STRING)
    DayType dayType;
}
