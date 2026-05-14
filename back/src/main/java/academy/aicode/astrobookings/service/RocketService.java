package academy.aicode.astrobookings.service;

import academy.aicode.astrobookings.exception.ResourceNotFoundException;
import academy.aicode.astrobookings.model.Rocket;
import academy.aicode.astrobookings.repository.RocketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RocketService {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(RocketService.class);
    private final RocketRepository repository;

    public RocketService(RocketRepository repository) {
        this.repository = repository;
    }

    public List<Rocket> getAllRockets() {
        return repository.findAll();
    }

    public Rocket getRocketById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rocket not found with id: " + id));
    }

    public Rocket createRocket(Rocket rocket) {
        rocket.setId(null); // Ensure it's a new rocket
        rocket.setDecommissioned(false);
        Rocket savedRocket = repository.save(rocket);
        LOGGER.info("Rocket created: {} (Name: {}, Capacity: {})", savedRocket.getId(), savedRocket.getName(), savedRocket.getCapacity());
        return savedRocket;
    }

    public Rocket updateRocket(UUID id, Rocket rocketDetails) {
        Rocket rocket = getRocketById(id);

        if (Boolean.TRUE.equals(rocket.isDecommissioned())) {
            throw new IllegalStateException("Cannot update a decommissioned rocket");
        }

        rocket.setName(rocketDetails.getName());
        rocket.setCapacity(rocketDetails.getCapacity());
        rocket.setRange(rocketDetails.getRange());

        Rocket updatedRocket = repository.save(rocket);
        LOGGER.info("Rocket updated: {}", updatedRocket.getId());
        return updatedRocket;
    }

    public void decommissionRocket(UUID id) {
        Rocket rocket = getRocketById(id);
        rocket.setDecommissioned(true);
        repository.save(rocket);
        LOGGER.info("Rocket decommissioned: {}", id);
    }

}
