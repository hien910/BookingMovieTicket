package com.example.bookingmovieticket.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertReviewRequest {
    String comment;
    Integer rating;
    Integer movieId;
}
