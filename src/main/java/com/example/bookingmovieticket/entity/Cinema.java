package com.example.bookingmovieticket.entity;

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
@Table(name = "cinemas")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false,unique = true)
    String name;

    String location;

    @Column(columnDefinition = "TEXT")
    String description;

    String poster;

    Date createdAt;
    Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "system_cinema_id")
    SystemCinema systemCinema;
}
