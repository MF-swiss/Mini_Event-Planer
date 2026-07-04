package ch.swiss.eventbackend.seeder;

import ch.swiss.eventbackend.model.Artist;
import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.model.Location;
import ch.swiss.eventbackend.repository.ArtistRepository;
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
    private final ArtistRepository artistRepository;

    public Seeder(EventRepository eventRepository,
                  LocationRepository locationRepository,
                  ArtistRepository artistRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        // -----------------------------
        // Artists laden
        // -----------------------------
        InputStream artistStream = getClass().getResourceAsStream("/artists.json");
        List<Map<String, Object>> artistJson = mapper.readValue(artistStream, new TypeReference<>() {});
        List<Artist> artists = artistJson.stream().map(this::artistFromJson).toList();
        artistRepository.saveAll(artists);

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

                // Artist anhand Name finden
                String artistName = (String) json.get("artist");
                Artist artist = artistRepository.findAll()
                    .stream()
                    .filter(a -> a.getName().equalsIgnoreCase(artistName))
                    .findFirst()
                    .orElse(null);

                Event event = eventFromJson(json, location, artist);
            eventRepository.save(event);
        }
    }

    // -----------------------------
    // JSON → Entity Mapper
    // -----------------------------

    private Artist artistFromJson(Map<String, Object> json) {
        Artist artist = new Artist();
        artist.setName((String) json.get("name"));
        artist.setGenre((String) json.get("genre"));
        artist.setOrigin((String) json.get("origin"));
        artist.setExperience((String) json.get("experience"));
        artist.setDescription((String) json.get("description"));
        return artist;
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

    private Event eventFromJson(Map<String, Object> json, Location location, Artist artist) {
        Event event = new Event();
        event.setTitle((String) json.get("title"));
        event.setDescription((String) json.get("description"));
        event.setDate(LocalDate.parse((String) json.get("date")));
        event.setLocation(location);
        event.setArtist(artist);
        return event;
    }
}
