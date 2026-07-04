package ch.swiss.eventbackend.mapper;

import ch.swiss.eventbackend.dto.EventDTO;
import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.model.Location;
import ch.swiss.eventbackend.model.Artist;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventDTO toDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getTitle(),
                event.getDate(),
                event.getDescription(),
                event.getLocation() != null ? event.getLocation().getId() : null,
                event.getArtist() != null ? event.getArtist().getId() : null
        );
    }

    public Event toEntity(EventDTO dto, Location location, Artist artist) {
        Event event = new Event();
        event.setTitle(dto.title());
        event.setDate(dto.date());
        event.setDescription(dto.description());
        event.setLocation(location);
        event.setArtist(artist);
        return event;
    }
}
