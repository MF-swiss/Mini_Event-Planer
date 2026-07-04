package ch.swiss.eventbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.swiss.eventbackend.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
