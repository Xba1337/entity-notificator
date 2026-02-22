package ru.spring.kafka.eventnotificator.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.spring.kafka.eventnotificator.security.jwt.UserPrincipal;

import java.util.List;

@RestController
@RequestMapping("notifications")
public class EventNotificatorController {

    private static final Logger log = LoggerFactory.getLogger(EventNotificatorController.class);

    private final EventNotificatorService eventNotificatorService;
    private final EventNotificatorMapper eventNotificatorMapper;

    public EventNotificatorController(EventNotificatorService eventNotificatorService, EventNotificatorMapper eventNotificatorMapper) {
        this.eventNotificatorService = eventNotificatorService;
        this.eventNotificatorMapper = eventNotificatorMapper;
    }

    @GetMapping
    public ResponseEntity<List<EventChangeMessage>> getNotifications() {
        log.info("Get notifications");

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        String authenticatedUserLogin = userPrincipal.role();

        List<EventNotificator> notifications = eventNotificatorService.getNotifications(authenticatedUserLogin);

        return ResponseEntity.status(HttpStatus.OK)
                .body(notifications.stream()
                        .map(eventNotificatorMapper::toDto)
                        .toList()
                );
    }

    @PostMapping
    public ResponseEntity<Void> markAllNotificationsAsRead() {
        log.info("Mark all notifications as read");

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        String authenticatedUserLogin = userPrincipal.role();

        eventNotificatorService.markAllNotificationsAsRead(authenticatedUserLogin);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
