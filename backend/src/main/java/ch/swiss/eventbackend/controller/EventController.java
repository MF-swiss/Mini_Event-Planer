package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.dto.EventDTO;
import ch.swiss.eventbackend.mapper.EventMapper;
import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.model.Location;
import ch.swiss.eventbackend.model.Artist;
import ch.swiss.eventbackend.service.EventService;
import ch.swiss.eventbackend.service.LocationService;
import ch.swiss.eventbackend.service.ArtistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final LocationService locationService;
    private final ArtistService artistService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, LocationService locationService,
                           ArtistService artistService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.artistService = artistService;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents()
                .stream()
                .map(eventMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Long id) {
        return eventMapper.toDTO(eventService.getEventById(id));
    }

    @PostMapping
    public EventDTO createEvent(@RequestBody EventDTO dto) {

        Location location = locationService.getLocationById(dto.locationId());
        Artist artist = artistService.getArtistById(dto.artistId());

        Event event = eventMapper.toEntity(dto, location, artist);
        Event saved = eventService.saveEvent(event);

        return eventMapper.toDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
