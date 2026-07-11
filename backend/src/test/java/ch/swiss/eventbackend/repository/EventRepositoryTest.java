package ch.swiss.eventbackend.repository;

import ch.swiss.eventbackend.model.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void saveEvent_persistsAndCanBeFound() {
        Event e = new Event();
        e.setTitle("Repo Test");
        e.setDescription("Repo Desc");

        Event saved = eventRepository.save(e);

        assertNotNull(saved.getId());
        Optional<Event> found = eventRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Repo Test", found.get().getTitle());
    }

    @Test
    void deleteEvent_removesFromDatabase() {
        Event e = new Event();
        e.setTitle("Delete Repo Test");
        e.setDescription("Repo Desc");

        Event saved = eventRepository.save(e);
        eventRepository.deleteById(saved.getId());

        assertFalse(eventRepository.findById(saved.getId()).isPresent());
    }
}
