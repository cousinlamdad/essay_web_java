package com.example.essayweb;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/essays")
public class EssayApiController {

    private final EssayRepository essays;

    public EssayApiController(EssayRepository essays) {
        this.essays = essays;
    }

    @GetMapping
    public List<EssaySummaryDto> list() {
        return essays.findAllByOrderByCreatedAtDesc().stream()
                .map(e -> new EssaySummaryDto(e.getId(), e.getTitle(), e.getCreatedAt()))
                .toList();
    }

    @GetMapping("/{id}")
    public EssayDetailDto detail(@PathVariable int id) {
        Essay essay = essays.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Essay not found"));
        return new EssayDetailDto(essay.getId(), essay.getTitle(), essay.getContent(), essay.getCreatedAt());
    }

    @PostMapping
    public ResponseEntity<Map<String, Integer>> create(@RequestBody CreateEssayRequest request) {
        if (request.title() == null || request.title().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        }
        Essay saved = essays.save(new Essay(request.title(), request.content()));
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", saved.getId()));
    }
}
