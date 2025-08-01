package com.countries.service;

import com.countries.dto.response.CountryDataResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class CountryService {
    @Value("${REST_URL}")
    private String URL;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<CountryDataResponseDTO> findAllCountries() {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/all?fields=name,region,capital,population,flags"))
                    .GET().build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(httpResponse.body(), new TypeReference<>() {
            });
        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException("Error while search all countries: " + exception.getMessage());
        }
    }

    public List<CountryDataResponseDTO> findCountriesByRegion(String region) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/region/" + region))
                    .GET().build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(httpResponse.body(), new TypeReference<>() {
            });
        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException("Error while search countries by region: " + exception.getMessage());
        }
    }

    public List<CountryDataResponseDTO> findCountryByName(String countryName) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/name/" + countryName))
                    .GET().build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(httpResponse.body(), new TypeReference<List<CountryDataResponseDTO>>() {
            });
        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException("Error while search country by name: " + exception.getMessage());
        }
    }
}
