package ru.spring.kafka.eventnotificator.notification;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventNotificatorMapper {

    public EventChangeMessage toDto(EventNotificator eventNotificator) {
        return new EventChangeMessage(
                eventNotificator.eventId(),
                eventNotificator.changedByUserId(),
                eventNotificator.ownerId(),
                eventNotificator.changes(),
                eventNotificator.participants()
        );
    }

    public EventNotificatorEntity toEntity(EventChangeMessage eventChangeMessage) {
        return new EventNotificatorEntity(
                null,
                eventChangeMessage.eventId(),
                eventChangeMessage.changedByUserId(),
                eventChangeMessage.ownerId(),
                eventChangeMessage.changes(),
                eventChangeMessage.participants(),
                LocalDateTime.now(),
                false
        );
    }

    public EventNotificator toDomain(EventNotificatorEntity eventNotificatorEntity){
        return new EventNotificator(
                eventNotificatorEntity.getEventId(),
                eventNotificatorEntity.getChangedByUserId(),
                eventNotificatorEntity.getOwnerId(),
                eventNotificatorEntity.getChanges(),
                eventNotificatorEntity.getParticipants()
        );
    }
}
