package academy.aicode.astrobookings.repository;

import academy.aicode.astrobookings.model.Identifiable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class InMemoryRepository<T extends Identifiable> {
    protected final Map<UUID, T> entities = new ConcurrentHashMap<>();

    public List<T> findAll() {
        return new ArrayList<>(entities.values());
    }

    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(entities.get(id));
    }

    public T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        entities.put(entity.getId(), entity);
        return entity;
    }

    public void deleteById(UUID id) {
        entities.remove(id);
    }
}
