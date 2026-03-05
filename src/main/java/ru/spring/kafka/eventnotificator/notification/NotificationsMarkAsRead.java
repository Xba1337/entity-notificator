package ru.spring.kafka.eventnotificator.notification;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record NotificationsMarkAsRead(
        @NotEmpty(message = "Request cannot be empty.")
        List<Integer> notificationsIds
) {
}
