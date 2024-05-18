package com.example.bookingmovieticket.model.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusTicket {
    PAID("PAID"),
    UNPAID("UNPAID"),
    CANCELLED("CANCELLED"),
    REFUND("REFUND");
    private final String value;
}
