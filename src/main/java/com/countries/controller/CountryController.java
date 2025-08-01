package com.countries.controller;

import com.countries.dto.response.CountryResponseDTO;
import com.countries.service.CountryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public List<CountryResponseDTO> getAllCountries() {
        return countryService.findAllCountries();
    }

    @GetMapping("/region/{region}")
    public List<CountryResponseDTO> getCountriesByRegion(@PathVariable String region) {
        return countryService.findCountriesByRegion(region);
    }
}
