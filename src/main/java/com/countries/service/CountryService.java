package com.countries.service;

import com.countries.dto.response.CountryResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class CountryService {
    private static final String URL = "https://restcountries.com/v3.1";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<CountryResponseDTO> findAllCountries() {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/all?fields=name,region,capital,population,flags"))
                    .GET().build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(httpResponse.body(), new TypeReference<>() {});
        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException("Error while search all countries: " + exception.getMessage());
        }
    }
}
