package academy.aicode.astrobookings.model;

import java.util.UUID;

public interface Identifiable {
    UUID getId();
    void setId(UUID id);
}
