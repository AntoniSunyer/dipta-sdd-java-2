package academy.aicode.astrobookings.controller;

import academy.aicode.astrobookings.model.Launch;
import academy.aicode.astrobookings.model.LaunchStatus;
import academy.aicode.astrobookings.service.LaunchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/launches")
public class LaunchController {
    private final LaunchService service;

    public LaunchController(LaunchService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Launch> createLaunch(@Valid @RequestBody Launch launch) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createLaunch(launch));
    }

    @GetMapping
    public ResponseEntity<List<Launch>> getAllLaunches() {
        return ResponseEntity.ok(service.getAllLaunches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Launch> getLaunchById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getLaunchById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Launch> updateLaunchStatus(@PathVariable UUID id, @RequestBody LaunchStatus status) {
        return ResponseEntity.ok(service.updateLaunchStatus(id, status));
    }
}
