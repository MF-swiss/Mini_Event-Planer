package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.dto.EventDTO;
import ch.swiss.eventbackend.mapper.EventMapper;
import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.model.Location;
import ch.swiss.eventbackend.model.DJ;
import ch.swiss.eventbackend.service.EventService;
import ch.swiss.eventbackend.service.LocationService;
import ch.swiss.eventbackend.service.DJService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final LocationService locationService;
    private final DJService djService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, LocationService locationService,
                           DJService djService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.djService = djService;
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
        DJ dj = djService.getDJById(dto.djId());

        Event event = eventMapper.toEntity(dto, location, dj);
        Event saved = eventService.saveEvent(event);

        return eventMapper.toDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
