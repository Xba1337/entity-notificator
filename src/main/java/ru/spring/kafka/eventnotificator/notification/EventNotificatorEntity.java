package ru.spring.kafka.eventnotificator.notification;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "user_notifications")
public class EventNotificatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer eventId;

    private Integer changedByUserId;

    private String userLogin;

    private Integer ownerId;

    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, FieldChange<?>> changes;

    private LocalDateTime createdAt;

    private boolean read;

    public EventNotificatorEntity() {
    }

    public EventNotificatorEntity(Integer id, Integer eventId, Integer changedByUserId, String userLogin, Integer ownerId, Map<String, FieldChange<?>> changes, LocalDateTime createdAt, boolean read) {
        this.id = id;
        this.eventId = eventId;
        this.changedByUserId = changedByUserId;
        this.userLogin = userLogin;
        this.ownerId = ownerId;
        this.changes = changes;
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

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
