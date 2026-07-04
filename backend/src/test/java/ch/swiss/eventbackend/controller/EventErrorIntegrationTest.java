package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventErrorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setup() {
        // DB leeren → garantiert reproduzierbar
        eventRepository.deleteAll();
    }

    @Test
    void getEvent_notFound_returns404() throws Exception {
        // Testet: GET /events/{id} liefert 404 Not Found, wenn das Event nicht existiert
        mockMvc.perform(get("/events/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEvent_invalidBody_returns400() throws Exception {
        // Testet: POST /events liefert 400 Bad Request, wenn Pflichtfelder fehlen
        String invalidJson = """
            {
              "title": "",
              "description": ""
            }
            """;

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
