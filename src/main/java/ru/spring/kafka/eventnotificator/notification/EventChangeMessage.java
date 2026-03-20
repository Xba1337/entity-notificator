package ru.spring.kafka.eventnotificator.notification;

import java.util.List;
import java.util.Map;

public record EventChangeMessage(
        Integer eventId,
        Integer changedByUserId,
        Integer ownerId,
        Map<String, FieldChange<?>> changes,
        List<String> participants
) {
}
