package ru.spring.kafka.eventnotificator.notification;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<EventNotificatorEntity> toEntities(EventChangeMessage eventChangeMessage) {
        return eventChangeMessage.participants().stream()
                .map(participantLogin -> new EventNotificatorEntity(
                        null,
                        eventChangeMessage.eventId(),
                        eventChangeMessage.changedByUserId(),
                        participantLogin,
                        eventChangeMessage.ownerId(),
                        eventChangeMessage.changes(),
                        LocalDateTime.now(),
                        false
                        ))
                .toList();
    }

    public EventNotificator toDomain(EventNotificatorEntity eventNotificatorEntity) {
        return new EventNotificator(
                eventNotificatorEntity.getEventId(),
                eventNotificatorEntity.getChangedByUserId(),
                eventNotificatorEntity.getOwnerId(),
                eventNotificatorEntity.getChanges(),
                List.of(eventNotificatorEntity.getUserLogin())
        );
    }
}
