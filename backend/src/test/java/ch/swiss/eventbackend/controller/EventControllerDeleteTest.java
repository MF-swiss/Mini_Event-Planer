package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.exception.ResourceNotFoundException;
import ch.swiss.eventbackend.service.EventService;
import ch.swiss.eventbackend.service.LocationService;
import ch.swiss.eventbackend.service.ArtistService;
import ch.swiss.eventbackend.mapper.EventMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    // ---------------------------------------------------------
    // Alle Abhängigkeiten des Controllers müssen gemockt werden
    // ---------------------------------------------------------
    @MockBean
    private EventService eventService;

    @MockBean
    private LocationService locationService;

    @MockBean
    private ArtistService artistService;

    @MockBean
    private EventMapper eventMapper;

    @Test
    void deleteEvent_existingId_returns204() throws Exception {
        // ---------------------------------------------------------
        // Arrange: Event existiert → Service gibt true zurück
        // ---------------------------------------------------------
        when(eventService.deleteEvent(1L)).thenReturn(true);

        // ---------------------------------------------------------
        // Act + Assert: DELETE /events/1 → 204 No Content
        // ---------------------------------------------------------
        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEvent_nonExistingId_returns404() throws Exception {
        // ---------------------------------------------------------
        // Arrange: Event existiert NICHT → Service gibt false zurück
        // Controller wirft ResourceNotFoundException
        // ---------------------------------------------------------
        when(eventService.deleteEvent(999L)).thenReturn(false);

        // ---------------------------------------------------------
        // Act + Assert: DELETE /events/999 → 404 Not Found
        // ---------------------------------------------------------
        mockMvc.perform(delete("/events/999"))
                .andExpect(status().isNotFound());
    }
}
