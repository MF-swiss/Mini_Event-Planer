package ch.swiss.eventbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.swiss.eventbackend.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}