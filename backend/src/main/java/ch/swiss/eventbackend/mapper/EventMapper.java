package ch.swiss.eventbackend.mapper;

import ch.swiss.eventbackend.dto.EventDTO;
import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.model.Location;
import ch.swiss.eventbackend.model.DJ;
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
                event.getDj() != null ? event.getDj().getId() : null
        );
    }

    public Event toEntity(EventDTO dto, Location location, DJ dj) {
        Event event = new Event();
        event.setTitle(dto.title());
        event.setDate(dto.date());
        event.setDescription(dto.description());
        event.setLocation(location);
        event.setDj(dj);
        return event;
    }
}
