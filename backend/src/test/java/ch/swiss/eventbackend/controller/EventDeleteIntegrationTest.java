
package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventDeleteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    private Long savedEventId;

    @BeforeEach
    void setup() {
        // Vor jedem Test DB leeren → garantiert reproduzierbar
        eventRepository.deleteAll();

        // Ein Event speichern, damit DELETE es löschen kann
        Event e = new Event();
        e.setTitle("Delete Test Event");
        e.setDescription("To be deleted");
        savedEventId = eventRepository.save(e).getId();
    }

    @Test
    void deleteEvent_existingId_returns204() throws Exception {
        // Testet: DELETE /events/{id} löscht ein existierendes Event und liefert 204 No Content
        mockMvc.perform(delete("/events/" + savedEventId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEvent_nonExistingId_returns404() throws Exception {
        // Testet: DELETE /events/{id} liefert 404 Not Found, wenn das Event nicht existiert
        mockMvc.perform(delete("/events/999999"))
                .andExpect(status().isNotFound());
    }
}
