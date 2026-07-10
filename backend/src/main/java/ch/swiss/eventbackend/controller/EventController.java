package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.dto.EventDTO;
import ch.swiss.eventbackend.mapper.EventMapper;
import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.model.Location;
import ch.swiss.eventbackend.model.Artist;
import ch.swiss.eventbackend.service.EventService;
import ch.swiss.eventbackend.service.LocationService;
import ch.swiss.eventbackend.service.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    // GET /events → 200 OK
    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents()
                .stream()
                .map(eventMapper::toDTO)
                .toList();
    }

    // GET /events/{id} → 200 OK oder 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity.ok(eventMapper.toDTO(event)); // 200
    }

    // POST /events → 201 Created
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO dto) {

        // Validierung → 400 Bad Request
        if (dto.title() == null || dto.title().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Location location = locationService.getLocationById(dto.locationId());
        Artist artist = artistService.getArtistById(dto.artistId());

        Event event = eventMapper.toEntity(dto, location, artist);
        Event saved = eventService.saveEvent(event);
        EventDTO responseDto = eventMapper.toDTO(saved);

        // 201 Created + Location Header
        return ResponseEntity
                .created(URI.create("/events/" + saved.getId()))
                .body(responseDto);
    }

    // PUT /events/{id} → 200 OK, 400 Bad Request oder 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO dto) {
        Event existing = eventService.getEventById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build(); // 404
        }

        // Validierung → 400 Bad Request
        if (dto.title() == null || dto.title().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Location location = locationService.getLocationById(dto.locationId());
        Artist artist = artistService.getArtistById(dto.artistId());

        existing.setTitle(dto.title());
        existing.setDate(dto.date());
        existing.setDescription(dto.description());
        existing.setLocation(location);
        existing.setArtist(artist);

        Event updated = eventService.saveEvent(existing);
        return ResponseEntity.ok(eventMapper.toDTO(updated)); // 200
    }

    // DELETE /events/{id} → 204 No Content oder 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        boolean deleted = eventService.deleteEvent(id);
        if (!deleted) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity.noContent().build(); // 204
    }
}