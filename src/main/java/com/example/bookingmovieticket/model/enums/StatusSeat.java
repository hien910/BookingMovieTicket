package com.example.bookingmovieticket.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusSeat{
    ACTIVE("ACTIVE"),
    BLOCK("BLOCK");
    private final String value;
}
