package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.dto.EventDTO;
import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.model.Location;
import ch.swiss.eventbackend.model.Artist;
import ch.swiss.eventbackend.service.EventService;
import ch.swiss.eventbackend.service.LocationService;
import ch.swiss.eventbackend.service.ArtistService;
import ch.swiss.eventbackend.mapper.EventMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerPostTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private LocationService locationService;

    @MockBean
    private ArtistService artistService;

    @MockBean
    private EventMapper eventMapper;

    @Test
    void createEvent_validRequest_returns201() throws Exception {

        // ---------------------------------------------------------
        // Arrange: Location + Artist (werden vom Controller geprüft)
        // ---------------------------------------------------------
        Location loc = new Location();
        Artist art = new Artist();

        // ---------------------------------------------------------
        // Arrange: Entity, das gespeichert wird
        // ---------------------------------------------------------
        Event entity = new Event();
        entity.setId(10L);
        entity.setTitle("New Event");
        entity.setDescription("Created via POST");
        entity.setDate(LocalDate.parse("2026-01-01"));
        entity.setLocation(loc);
        entity.setArtist(art);

        // ---------------------------------------------------------
        // Arrange: DTO, das als Response zurückgegeben wird
        // ---------------------------------------------------------
        EventDTO responseDto = new EventDTO(
                10L,
                "New Event",
                LocalDate.parse("2026-01-01"),
                "Created via POST",
                1L,
                1L
        );

        // ---------------------------------------------------------
        // Mocks: Services + Mapper
        // ---------------------------------------------------------
        when(locationService.getLocationById(1L)).thenReturn(loc);
        when(artistService.getArtistById(1L)).thenReturn(art);

        when(eventMapper.toEntity(any(EventDTO.class), any(Location.class), any(Artist.class)))
                .thenReturn(entity);

        when(eventService.saveEvent(entity)).thenReturn(entity);

        when(eventMapper.toDTO(entity)).thenReturn(responseDto);

        // ---------------------------------------------------------
        // JSON-Body für POST
        // ---------------------------------------------------------
        String json = """
            {
              "title": "New Event",
              "date": "2026-01-01",
              "description": "Created via POST",
              "locationId": 1,
              "artistId": 1
            }
            """;

        // ---------------------------------------------------------
        // Act + Assert: POST /events
        // ---------------------------------------------------------
        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.title").value("New Event"))
                .andExpect(jsonPath("$.description").value("Created via POST"));
    }
}
