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
@CrossOrigin(origins = "*")
public class RocketController {
    private final RocketService service;

    public RocketController(RocketService service) {
        this.service = service;
    }

    @GetMapping
    public List<Rocket> getAllRockets() {
        return service.getAllRockets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rocket> getRocketById(@PathVariable UUID id) {
        return service.getRocketById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rocket createRocket(@Valid @RequestBody Rocket rocket) {
        return service.createRocket(rocket);
    }

    @PutMapping("/{id}")
    public Rocket updateRocket(@PathVariable UUID id, @Valid @RequestBody Rocket rocket) {
        return service.updateRocket(id, rocket);
    }

    @PatchMapping("/{id}/decommission")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void decommissionRocket(@PathVariable UUID id) {
        service.decommissionRocket(id);
    }
}
