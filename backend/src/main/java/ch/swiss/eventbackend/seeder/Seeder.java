package ch.swiss.eventbackend.seeder;

import ch.swiss.eventbackend.model.DJ;
import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.model.Location;
import ch.swiss.eventbackend.repository.DJRepository;
import ch.swiss.eventbackend.repository.EventRepository;
import ch.swiss.eventbackend.repository.LocationRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class Seeder implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final DJRepository djRepository;

    public Seeder(EventRepository eventRepository,
                  LocationRepository locationRepository,
                  DJRepository djRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.djRepository = djRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        // -----------------------------
        // DJs laden
        // -----------------------------
        InputStream djStream = getClass().getResourceAsStream("/djs.json");
        List<Map<String, Object>> djJson = mapper.readValue(djStream, new TypeReference<>() {});
        List<DJ> djs = djJson.stream().map(this::djFromJson).toList();
        djRepository.saveAll(djs);

        // -----------------------------
        // Locations laden
        // -----------------------------
        InputStream locStream = getClass().getResourceAsStream("/locations.json");
        List<Map<String, Object>> locJson = mapper.readValue(locStream, new TypeReference<>() {});
        List<Location> locations = locJson.stream().map(this::locationFromJson).toList();
        locationRepository.saveAll(locations);

        // -----------------------------
        // Events laden
        // -----------------------------
        InputStream eventStream = getClass().getResourceAsStream("/events.json");
        List<Map<String, Object>> eventJson = mapper.readValue(eventStream, new TypeReference<>() {});

        for (Map<String, Object> json : eventJson) {

            // Location anhand Name finden
            String locationName = (String) json.get("location");
            Location location = locationRepository.findAll()
                    .stream()
                    .filter(l -> l.getName().equalsIgnoreCase(locationName))
                    .findFirst()
                    .orElse(null);

            // DJ anhand Name finden
            String djName = (String) json.get("dj");
            DJ dj = djRepository.findAll()
                    .stream()
                    .filter(d -> d.getName().equalsIgnoreCase(djName))
                    .findFirst()
                    .orElse(null);

            Event event = eventFromJson(json, location, dj);
            eventRepository.save(event);
        }
    }

    // -----------------------------
    // JSON → Entity Mapper
    // -----------------------------

    private DJ djFromJson(Map<String, Object> json) {
        DJ dj = new DJ();
        dj.setName((String) json.get("name"));
        dj.setGenre((String) json.get("genre"));
        dj.setOrigin((String) json.get("origin"));
        dj.setExperience((String) json.get("experience"));
        dj.setDescription((String) json.get("description"));
        return dj;
    }

    private Location locationFromJson(Map<String, Object> json) {
        Location location = new Location();
        location.setName((String) json.get("name"));
        location.setCity((String) json.get("city"));
        location.setCountry((String) json.get("country"));
        location.setType((String) json.get("type"));
        location.setCapacity((Integer) json.get("capacity"));
        location.setDescription((String) json.get("description"));
        return location;
    }

    private Event eventFromJson(Map<String, Object> json, Location location, DJ dj) {
        Event event = new Event();
        event.setTitle((String) json.get("title"));
        event.setDescription((String) json.get("description"));
        event.setDate(LocalDate.parse((String) json.get("date")));
        event.setLocation(location);
        event.setDj(dj);
        return event;
    }
}
