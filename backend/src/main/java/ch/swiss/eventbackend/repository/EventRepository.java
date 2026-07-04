package ch.swiss.eventbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.swiss.eventbackend.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
