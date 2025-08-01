package com.countries.service;

import com.countries.dto.response.CountryDataResponseDTO;
import com.countries.exception.CountryNotFoundException;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class CountryServiceTest {

    private static WireMockServer wireMockServer;
    private CountryService countryService;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor("localhost", 8089);
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @BeforeEach
    void SetUp() {
        countryService = new CountryService();
        ReflectionTestUtils.setField(countryService, "URL", "http://localhost:8089");
    }

    @Test
    void shouldReturnListOfCountries_whenFindAllCountriesCalled() {
        String mockJson = """
                [
                    {
                        "name": {"common": "Brazil"},
                        "region": "Americas",
                        "capital": ["Brasília"],
                        "population": "211000000",
                        "flags": {"png":"http://example.com/brazil.png"}
                    }
                ]
                """;

        stubFor(get(urlPathMatching("/all"))
                .withQueryParam("fields", equalTo("name,region,capital,population,flags"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockJson)));

        List<CountryDataResponseDTO> result = countryService.findAllCountries();

        assertFalse(result.isEmpty());
        assertEquals("Brazil", result.get(0).name().common());
    }

    @Test
    void shouldThrowException_whenRegionNotFound() {
        stubFor(get(urlEqualTo("/region/UnknownRegion"))
                .willReturn(aResponse().withStatus(404)));
        assertThrows(CountryNotFoundException.class, () ->
                countryService.findCountriesByRegion("UnknownRegion"));
    }
}
