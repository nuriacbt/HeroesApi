package es.heroesfactory.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import es.heroesfactory.dtos.AllHeroesResponse;
import es.heroesfactory.dtos.ErrorDTO;
import es.heroesfactory.dtos.HeroeDTO;
import es.heroesfactory.dtos.HeroesPageableResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeroesIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final Integer LIST_ALL_HEROES_SIZE = 115;

    private static final String AUTH_PARAM_NAME = "API-KEY";
    private static final String AUTH_TOKKEN = "8e366910";

    @Test
    public void testAllHeroes() {
        assertEquals((int) LIST_ALL_HEROES_SIZE, callAllHeroes().getHeroes().size());
    }

  @Test
  public void testPageableList() {
      String queryParams = "page=3&elements=10";
      HeroesPageableResponse response = this.restTemplate
            .getForObject(getUrl(null, queryParams), HeroesPageableResponse.class);

      assertEquals(LIST_ALL_HEROES_SIZE.longValue(), (long) response.getTotalElements());
      assertEquals(response.getNumPage(), 3);
      assertEquals(response.getHeroes().size(), 10);
  }

  @Test
  public void testGetHeroeById() {
      final Integer HID = 1;
      HeroeDTO response = this.restTemplate
            .getForObject(getUrl("/"+HID, null), HeroeDTO.class);
      assertEquals(response.getId(), HID);
  }

  @Test
  public void testGetHeroeByName() {
      final String NAME_TO_SEARCH = "woman";
      final Integer LIST_SIZE = 2;
      String queryParam = "name=".concat(NAME_TO_SEARCH);

      AllHeroesResponse response = this.restTemplate
            .getForObject(getUrl("/search", queryParam), AllHeroesResponse.class);

      assertEquals(response.getHeroes().size(), LIST_SIZE);
      assertTrue(response.getHeroes().get(0).getName().toLowerCase().contains(NAME_TO_SEARCH));
  }

  @Test
  public void testInsertHeroe() {
      HeroeDTO body = HeroeDTO.builder().name("TEST HERO")
              .speed(55).intelligence(55).combat(55).build();

      ResponseEntity<Void> response = this.restTemplate
              .postForEntity(getUrl(null, null), body, Void.class);

      assertEquals(response.getStatusCode(), HttpStatus.CREATED);

      AllHeroesResponse allResponse = callAllHeroes();
      assertEquals(allResponse.getHeroes().get(allResponse.getHeroes().size()-1).getName(), "TEST HERO");
  }

  @Test
  public void testUpdatetHeroe() {
      HeroeDTO body = HeroeDTO.builder().id(730).name("Zoom").imageUrl(null)
            .speed(55).intelligence(55).combat(55).build();

      this.restTemplate.put(getUrl(null, null), body);
      HeroeDTO response = this.restTemplate
            .getForObject(getUrl("/730", null), HeroeDTO.class);

      assertEquals(response.getSpeed(), 55);
      assertEquals(response.getCombat(), 55);
      assertEquals(response.getIntelligence(), 55);
      assertNull(response.getImageUrl());
  }

  @Test
  public void testDeleteHeroe() throws JsonProcessingException {
      final int HID = 10;
      this.restTemplate.delete(getUrl("/"+HID, null));

      String responseError = this.restTemplate
            .getForObject(getUrl("/"+HID, null), String.class);

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
      objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      objectMapper.registerModule(new JavaTimeModule());

      ErrorDTO error = objectMapper.readValue(responseError, ErrorDTO.class);
      assertEquals(error.getStatus(), HttpStatus.NOT_FOUND.value());
  }

  private AllHeroesResponse callAllHeroes() {
      return this.restTemplate
              .getForObject(getUrl("/all", null), AllHeroesResponse.class);
  }

  private String getUrl(String specificPath, String queryParams) {

      StringBuilder build = new StringBuilder("http://localhost:").append(port).append("/hero-api/v1/heroes");
      if (specificPath != null) {
        build.append(specificPath);
      }
      build.append("?").append(AUTH_PARAM_NAME).append("=").append(AUTH_TOKKEN);
      if (queryParams != null) {
        build.append("&").append(queryParams);
      }
      return build.toString();
  }
}
