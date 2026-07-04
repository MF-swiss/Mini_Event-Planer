package ch.swiss.eventbackend.service;

import ch.swiss.eventbackend.model.Event;
import ch.swiss.eventbackend.repository.EventRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    // ⭐ KORREKTUR: deleteEvent liefert jetzt boolean statt void
    public boolean deleteEvent(Long id) {

        // Wenn das Event nicht existiert → 404 im Controller
        if (!eventRepository.existsById(id)) {
            return false;
        }

        // Wenn es existiert → löschen
        eventRepository.deleteById(id);
        return true; // 204 No Content
    }
}
