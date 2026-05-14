package academy.aicode.astrobookings.controller;

import academy.aicode.astrobookings.model.Rocket;
import academy.aicode.astrobookings.service.RocketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rockets")
public class RocketController {
    private final RocketService service;

    public RocketController(RocketService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Rocket>> getAllRockets() {
        return ResponseEntity.ok(service.getAllRockets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rocket> getRocketById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getRocketById(id));
    }

    @PostMapping
    public ResponseEntity<Rocket> createRocket(@Valid @RequestBody Rocket rocket) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRocket(rocket));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rocket> updateRocket(@PathVariable UUID id, @Valid @RequestBody Rocket rocket) {
        return ResponseEntity.ok(service.updateRocket(id, rocket));
    }

    @PatchMapping("/{id}/decommission")
    public ResponseEntity<Void> decommissionRocket(@PathVariable UUID id) {
        service.decommissionRocket(id);
        return ResponseEntity.noContent().build();
    }
}
