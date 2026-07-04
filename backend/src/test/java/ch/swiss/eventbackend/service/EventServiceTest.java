package ch.swiss.eventbackend.service;

import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Test
    void getAllEvents_returnsEventsFromRepository() {
        // Mock Repository
        EventRepository repo = Mockito.mock(EventRepository.class);

        // Mock Daten
        Event e1 = new Event();
        e1.setId(1L);
        e1.setTitle("Test Event");

        when(repo.findAll()).thenReturn(List.of(e1));

        // Service mit Mock
        EventService service = new EventService(repo);

        // Test
        List<Event> result = service.getAllEvents();

        assertEquals(1, result.size());
        assertEquals("Test Event", result.get(0).getTitle());

        // Sicherstellen, dass findAll() genau einmal aufgerufen wurde
        verify(repo, times(1)).findAll();
    }
}
