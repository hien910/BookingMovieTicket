package com.example.bookingmovieticket.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SeatType{
    STANDARD("STANDARD"),
    VIP("VIP"),
    COUPLE("COUPLE");
    private final String value;
}
