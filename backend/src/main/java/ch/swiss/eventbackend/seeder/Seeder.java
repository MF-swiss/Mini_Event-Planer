package ch.swiss.eventbackend.seeder;

import ch.swiss.eventbackend.mapper.DJMapper;
import ch.swiss.eventbackend.mapper.EventMapper;
import ch.swiss.eventbackend.mapper.LocationMapper;
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
import java.util.List;
import java.util.Map;

@Component
public class Seeder implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final DJRepository djRepository;

    public Seeder(EventRepository eventRepository, LocationRepository locationRepository, DJRepository djRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.djRepository = djRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        // DJs laden
        InputStream djStream = getClass().getResourceAsStream("/djs.json");
        List<Map<String, Object>> djJson = mapper.readValue(djStream, new TypeReference<>() {});
        List<DJ> djs = djJson.stream().map(DJMapper::fromJson).toList();
        djRepository.saveAll(djs);

        // Locations laden
        InputStream locStream = getClass().getResourceAsStream("/locations.json");
        List<Map<String, Object>> locJson = mapper.readValue(locStream, new TypeReference<>() {});
        List<Location> locations = locJson.stream().map(LocationMapper::fromJson).toList();
        locationRepository.saveAll(locations);

        // Events laden
        InputStream eventStream = getClass().getResourceAsStream("/events.json");
        List<Map<String, Object>> eventJson = mapper.readValue(eventStream, new TypeReference<>() {});

        for (Map<String, Object> json : eventJson) {
            String locationName = (String) json.get("location");
            Location location = locationRepository.findAll()
                    .stream()
                    .filter(l -> l.getName().equalsIgnoreCase(locationName))
                    .findFirst()
                    .orElse(null);

            DJ dj = djs.get(0); // Beispiel: erster DJ

            Event event = EventMapper.fromJson(json, location, dj);
            eventRepository.save(event);
        }
    }
}
