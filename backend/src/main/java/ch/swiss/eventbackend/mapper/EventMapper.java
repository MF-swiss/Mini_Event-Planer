package ch.swiss.eventbackend.mapper;

import java.time.LocalDate;
import java.util.Map;

import ch.swiss.eventbackend.model.DJ;
import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.model.Location;

public class EventMapper {

    public static Event fromJson(Map<String, Object> json, Location location, DJ dj) {
        Event event = new Event();
        event.setTitle((String) json.get("title"));
        event.setDescription((String) json.get("description"));
        event.setDate(LocalDate.parse((String) json.get("date")));
        event.setLocation(location);
        event.setDj(dj);
        return event;
    }
}
