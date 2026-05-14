package academy.aicode.astrobookings.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> getHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("uptime", formatUptime(ManagementFactory.getRuntimeMXBean().getUptime()));
        return response;
    }

    private String formatUptime(long uptimeMs) {
        Duration duration = Duration.ofMillis(uptimeMs);
        return String.format("%d days, %d hours, %d minutes, %d seconds",
                duration.toDays(),
                duration.toHoursPart(),
                duration.toMinutesPart(),
                duration.toSecondsPart());
    }
}
