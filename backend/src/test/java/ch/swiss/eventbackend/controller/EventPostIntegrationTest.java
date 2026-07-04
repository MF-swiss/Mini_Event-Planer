package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventPostIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setup() {
        // DB leeren → jeder Test startet sauber
        eventRepository.deleteAll();
    }

    @Test
    void createEvent_validRequest_returns201AndSavesEvent() throws Exception {
        // Testet: POST /events erzeugt ein neues Event und liefert 201 Created + JSON zurück
        String validJson = """
            {
              "title": "New Event",
              "description": "Created via POST",
              "date": "2026-01-01",
              "locationId": 1,
              "artistId": 1
            }
            """;

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Event"))
                .andExpect(jsonPath("$.description").value("Created via POST"));
    }
}
