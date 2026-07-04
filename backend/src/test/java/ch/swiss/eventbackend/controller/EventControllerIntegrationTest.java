package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setup() {
        // Vor jedem Test DB leeren → reproduzierbare Tests
        eventRepository.deleteAll();

        // Testdaten vorbereiten
        Event e = new Event();
        e.setTitle("Integration Test Event");
        e.setDescription("Test Description");
        eventRepository.save(e);
    }

    @Test
    void getEvents_returnsListOfEvents() throws Exception {
        // Testet: GET /events liefert 200 OK und gibt gespeicherte Events als JSON zurück
        mockMvc.perform(get("/events")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Integration Test Event"));
    }
}
