package ru.spring.kafka.eventnotificator.notification;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "user_notifications")
public class EventNotificatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer eventId;

    private Integer changedByUserId;

    private Integer ownerId;

    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, FieldChange<?>> changes;

    @ElementCollection
    @CollectionTable(
            name = "participants",
            joinColumns = @JoinColumn(name = "event_change_id")
    )
    private List<String> participants;

    private LocalDateTime createdAt;

    private boolean read;

    public EventNotificatorEntity() {
    }

    public EventNotificatorEntity(Integer id, Integer eventId, Integer changedByUserId, Integer ownerId, Map<String, FieldChange<?>> changes, List<String> participants, LocalDateTime createdAt, boolean read) {
        this.id = id;
        this.eventId = eventId;
        this.changedByUserId = changedByUserId;
        this.ownerId = ownerId;
        this.changes = changes;
        this.participants = participants;
        this.createdAt = createdAt;
        this.read = read;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer userId) {
        this.ownerId = userId;
    }

    public Map<String, FieldChange<?>> getChanges() {
        return changes;
    }

    public void setChanges(Map<String, FieldChange<?>> changes) {
        this.changes = changes;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Integer getChangedByUserId() {
        return changedByUserId;
    }

    public void setChangedByUserId(Integer changedByUserId) {
        this.changedByUserId = changedByUserId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}
