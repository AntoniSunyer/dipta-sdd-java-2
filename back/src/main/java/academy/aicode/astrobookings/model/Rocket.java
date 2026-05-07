package academy.aicode.astrobookings.model;

import jakarta.validation.constraints.*;
import java.util.UUID;

public class Rocket {
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 9, message = "Capacity must be at most 9")
    private int capacity;

    @NotNull(message = "Range is required")
    private RocketRange range;
    private Boolean decommissioned;

    public Rocket() {}

    public Rocket(UUID id, String name, int capacity, RocketRange range, Boolean decommissioned) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.range = range;
        this.decommissioned = decommissioned;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public RocketRange getRange() {
        return range;
    }

    public void setRange(RocketRange range) {
        this.range = range;
    }

    public Boolean isDecommissioned() {
        return decommissioned;
    }

    public void setDecommissioned(Boolean decommissioned) {
        this.decommissioned = decommissioned;
    }
}
