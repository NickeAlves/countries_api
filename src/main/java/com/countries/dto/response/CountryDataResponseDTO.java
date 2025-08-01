package com.countries.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CountryDataResponseDTO(Flags flags, Name name, List<String> capital, String region, Long population) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Flags(String png, String svg) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Name(String common, String official) {
    }
}
