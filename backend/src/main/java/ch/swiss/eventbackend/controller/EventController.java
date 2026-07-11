package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.dto.EventDTO;
import ch.swiss.eventbackend.exception.ResourceNotFoundException;
import ch.swiss.eventbackend.mapper.EventMapper;
import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.model.Location;
import ch.swiss.eventbackend.model.Artist;
import ch.swiss.eventbackend.service.EventService;
import ch.swiss.eventbackend.service.LocationService;
import ch.swiss.eventbackend.service.ArtistService;
import jakarta.validation.Valid;
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

    // GET /events -> 200 OK
    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents()
                .stream()
                .map(eventMapper::toDTO)
                .toList();
    }

    // GET /events/{id} -> 200 OK oder 404 (via GlobalExceptionHandler)
    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            throw new ResourceNotFoundException("Event mit ID " + id + " wurde nicht gefunden");
        }
        return eventMapper.toDTO(event);
    }

    // POST /events -> 201 Created, 400 (Validation) oder 404 (Location/Artist unbekannt)
    // @Valid löst bei ungültigen Daten automatisch eine
    // MethodArgumentNotValidException aus, die zentral im
    // GlobalExceptionHandler behandelt wird - keine manuelle
    // if-Prüfung mehr nötig.
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO dto) {

        Location location = locationService.getLocationById(dto.locationId());
        if (location == null) {
            throw new ResourceNotFoundException("Location mit ID " + dto.locationId() + " wurde nicht gefunden");
        }

        Artist artist = artistService.getArtistById(dto.artistId());
        if (artist == null) {
            throw new ResourceNotFoundException("Artist mit ID " + dto.artistId() + " wurde nicht gefunden");
        }

        Event event = eventMapper.toEntity(dto, location, artist);
        Event saved = eventService.saveEvent(event);
        EventDTO responseDto = eventMapper.toDTO(saved);

        return ResponseEntity
                .created(URI.create("/events/" + saved.getId()))
                .body(responseDto);
    }

    // PUT /events/{id} -> 200 OK, 400 (Validation) oder 404 (Event/Location/Artist unbekannt)
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO dto) {
        Event existing = eventService.getEventById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Event mit ID " + id + " wurde nicht gefunden");
        }

        Location location = locationService.getLocationById(dto.locationId());
        if (location == null) {
            throw new ResourceNotFoundException("Location mit ID " + dto.locationId() + " wurde nicht gefunden");
        }

        Artist artist = artistService.getArtistById(dto.artistId());
        if (artist == null) {
            throw new ResourceNotFoundException("Artist mit ID " + dto.artistId() + " wurde nicht gefunden");
        }

        existing.setTitle(dto.title());
        existing.setDate(dto.date());
        existing.setDescription(dto.description());
        existing.setLocation(location);
        existing.setArtist(artist);

        Event updated = eventService.saveEvent(existing);
        return ResponseEntity.ok(eventMapper.toDTO(updated));
    }

    // DELETE /events/{id} -> 204 No Content oder 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        boolean deleted = eventService.deleteEvent(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Event mit ID " + id + " wurde nicht gefunden");
        }
        return ResponseEntity.noContent().build();
    }
}