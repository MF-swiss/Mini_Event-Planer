package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.dto.DJDTO;
import ch.swiss.eventbackend.mapper.DJMapper;
import ch.swiss.eventbackend.model.DJ;
import ch.swiss.eventbackend.service.DJService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/djs")
public class DJController {

    private final DJService djService;
    private final DJMapper djMapper;

    public DJController(DJService djService, DJMapper djMapper) {
        this.djService = djService;
        this.djMapper = djMapper;
    }

    @GetMapping
    public List<DJDTO> getAllDJs() {
        return djService.getAllDJs()
                .stream()
                .map(djMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public DJDTO getDJById(@PathVariable Long id) {
        return djMapper.toDTO(djService.getDJById(id));
    }

    @PostMapping
    public DJDTO createDJ(@RequestBody DJDTO dto) {
        DJ dj = djMapper.toEntity(dto);
        DJ saved = djService.saveDJ(dj);
        return djMapper.toDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteDJ(@PathVariable Long id) {
        djService.deleteDJ(id);
    }
}
