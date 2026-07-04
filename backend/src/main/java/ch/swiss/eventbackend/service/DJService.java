package ch.swiss.eventbackend.service;

import ch.swiss.eventbackend.model.DJ;
import ch.swiss.eventbackend.repository.DJRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DJService {

    private final DJRepository djRepository;

    public DJService(DJRepository djRepository) {
        this.djRepository = djRepository;
    }

    public List<DJ> getAllDJs() {
        return djRepository.findAll();
    }

    public DJ getDJById(Long id) {
        return djRepository.findById(id).orElse(null);
    }

    public DJ saveDJ(DJ dj) {
        return djRepository.save(dj);
    }

    public void deleteDJ(Long id) {
        djRepository.deleteById(id);
    }
}
