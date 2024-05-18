package com.example.bookingmovieticket.entity;

import com.example.bookingmovieticket.model.enums.SeatType;
import com.example.bookingmovieticket.model.enums.StatusSeat;
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
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer rowIndex;
    Integer colIndex;

    String code;


    @Enumerated(EnumType.STRING)
    SeatType seatType;

    @Enumerated(EnumType.STRING)
    StatusSeat status;

    Double price;

    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;
}