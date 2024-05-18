package com.example.bookingmovieticket.entity;

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
@Table(name = "system_cinema")
public class SystemCinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    String poster;

    @Column(columnDefinition = "TEXT")
    String description;

    public SystemCinema(String name, String poster, String description) {
        this.name = name;
        this.poster = poster;
        this.description = description;
    }
}
