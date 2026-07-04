package main.java.repository;

import main.java.model.DJ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DJRepository extends JpaRepository<DJ, Long> {
}
