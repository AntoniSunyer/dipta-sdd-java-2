package academy.aicode.astrobookings.service;

import academy.aicode.astrobookings.model.Launch;
import academy.aicode.astrobookings.model.LaunchStatus;
import academy.aicode.astrobookings.model.Rocket;
import academy.aicode.astrobookings.model.RocketRange;
import academy.aicode.astrobookings.repository.LaunchRepository;
import academy.aicode.astrobookings.repository.RocketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LaunchServiceTest {

    private LaunchRepository repository;
    private RocketRepository rocketRepository;
    private RocketService rocketService;
    private LaunchService service;
    private Rocket testRocket;

    @BeforeEach
    void setUp() {
        repository = new LaunchRepository();
        rocketRepository = new RocketRepository();
        rocketService = new RocketService(rocketRepository);
        service = new LaunchService(repository, rocketService);
        
        testRocket = rocketService.createRocket(new Rocket(null, "Falcon 9", 5, RocketRange.EARTH, false));
    }

    @Test
    void shouldCreateLaunch() {
        Launch launch = new Launch(null, testRocket.getId(), LocalDate.now(), new BigDecimal("1000"), 2, null);
        Launch created = service.createLaunch(launch);

        assertNotNull(created.getId());
        assertEquals(LaunchStatus.CREATED, created.getStatus());
        assertEquals(testRocket.getId(), created.getRocketId());
    }

    @Test
    void shouldThrowExceptionWhenRocketDoesNotExist() {
        Launch launch = new Launch(null, UUID.randomUUID(), LocalDate.now(), new BigDecimal("1000"), 2, null);
        assertThrows(IllegalArgumentException.class, () -> service.createLaunch(launch));
    }

    @Test
    void shouldUpdateStatus() {
        Launch launch = new Launch(null, testRocket.getId(), LocalDate.now(), new BigDecimal("1000"), 2, null);
        Launch created = service.createLaunch(launch);

        service.updateLaunchStatus(created.getId(), LaunchStatus.CONFIRMED);
        Launch updated = service.getLaunchById(created.getId());

        assertEquals(LaunchStatus.CONFIRMED, updated.getStatus());
    }
}
