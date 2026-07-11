package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.dto.EventDTO;
import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.service.EventService;
import ch.swiss.eventbackend.service.LocationService;
import ch.swiss.eventbackend.service.ArtistService;
import ch.swiss.eventbackend.mapper.EventMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerGetTest {

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
    void getEvents_returnsList() throws Exception {

        // ---------------------------------------------------------
        // Arrange: Entity vorbereiten (Service liefert Entities)
        // ---------------------------------------------------------
        Event e = new Event();
        e.setId(1L);
        e.setTitle("Test Event");
        e.setDescription("Desc");
        e.setDate(LocalDate.parse("2026-01-01"));

        // ---------------------------------------------------------
        // Arrange: DTO vorbereiten (Mapper wandelt Entity → DTO)
        // Reihenfolge MUSS exakt der Record-Signatur entsprechen:
        //
        // EventDTO(
        //   Long id,
        //   String title,
        //   LocalDate date,
        //   String description,
        //   Long locationId,
        //   Long artistId
        // )
        // ---------------------------------------------------------
        EventDTO dto = new EventDTO(
                1L,                                // id
                "Test Event",                      // title
                LocalDate.parse("2026-01-01"),     // date
                "Desc",                            // description
                1L,                                // locationId
                1L                                 // artistId
        );

        // ---------------------------------------------------------
        // Mocks: Service + Mapper
        // ---------------------------------------------------------
        when(eventService.getAllEvents()).thenReturn(List.of(e));
        when(eventMapper.toDTO(e)).thenReturn(dto);

        // ---------------------------------------------------------
        // Act + Assert: GET /events
        // ---------------------------------------------------------
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Event"));
    }
}
