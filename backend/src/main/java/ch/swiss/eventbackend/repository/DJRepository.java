package ch.swiss.eventbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.swiss.eventbackend.model.DJ;

public interface DJRepository extends JpaRepository<DJ, Long> {
}
