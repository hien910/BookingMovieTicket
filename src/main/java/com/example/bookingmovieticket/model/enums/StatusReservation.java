package com.example.bookingmovieticket.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusReservation {
    BOOKED("BOOKED"),
    CANCELLED("CANCELLED"),
    RESERVED("RESERVED");
    private final String value;
}
