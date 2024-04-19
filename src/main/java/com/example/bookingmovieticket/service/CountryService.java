package com.example.bookingmovieticket.service;

import com.example.bookingmovieticket.entity.Country;
import com.example.bookingmovieticket.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public List<Country> getAll(){
        return countryRepository.findAll();
    }
}
