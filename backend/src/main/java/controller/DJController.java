package main.java.controller;

import main.java.model.DJ;
import main.java.service.DJService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/djs")
public class DJController {

    private final DJService djService;

    public DJController(DJService djService) {
        this.djService = djService;
    }

    @GetMapping
    public List<DJ> getAllDJs() {
        return djService.getAllDJs();
    }

    @GetMapping("/{id}")
    public DJ getDJById(@PathVariable Long id) {
        return djService.getDJById(id);
    }

    @PostMapping
    public DJ createDJ(@RequestBody DJ dj) {
        return djService.saveDJ(dj);
    }

    @DeleteMapping("/{id}")
    public void deleteDJ(@PathVariable Long id) {
        djService.deleteDJ(id);
    }
}
