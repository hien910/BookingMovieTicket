package com.example.bookingmovieticket.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DayType{
    WEEKDAY("Ngày thường"),

    WEEKEND("Cuối tuần");
    private final String value;
}