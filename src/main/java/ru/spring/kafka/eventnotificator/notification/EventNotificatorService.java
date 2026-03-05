package ru.spring.kafka.eventnotificator.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventNotificatorService {

    private static final Logger log = LoggerFactory.getLogger(EventNotificatorService.class);

    private final EventNotificatorRepository eventNotificatorRepository;
    private final EventNotificatorMapper eventNotificatorMapper;

    public EventNotificatorService(EventNotificatorRepository eventNotificatorRepository, EventNotificatorMapper eventNotificatorMapper) {
        this.eventNotificatorRepository = eventNotificatorRepository;
        this.eventNotificatorMapper = eventNotificatorMapper;
    }


    public List<EventNotificator> getNotifications(String login) {
        log.info("Getting notifications");

        List<EventNotificatorEntity> eventNotificatorEntities = eventNotificatorRepository.findAll();
        List<EventNotificator> allNotifications = eventNotificatorEntities.stream()
                .map(eventNotificatorMapper::toDomain)
                .toList();

        List<EventNotificator> notifications = allNotifications.stream()
                .filter(eventNotificator -> eventNotificator.participants() != null &&
                        eventNotificator.participants().stream()
                                .anyMatch(p -> p.equalsIgnoreCase(login)))
                .collect(Collectors.toList());

        log.info("Found {} notifications", notifications.size());

        return notifications;
    }

    @Transactional
    public void markAllNotificationsAsRead(String login, NotificationsMarkAsRead notificationsMarkAsRead) {
        log.info("Marking notification");

        List<Integer> notificationIdsForRead = notificationsMarkAsRead.notificationsIds();
        if (notificationIdsForRead.isEmpty()) {
            throw new IllegalArgumentException("Invalid request parameters");
        }

        int numberOfUpdatedNotifications = eventNotificatorRepository.markNotificationsAsRead(login, notificationIdsForRead);

        log.info("Updated {} notifications", numberOfUpdatedNotifications);
    }

    @Transactional
    public void saveNotification(EventChangeMessage notification){
        log.info("Saving notification");

        List<EventNotificatorEntity> eventNotificatorEntities = eventNotificatorMapper.toEntities(notification);

        List<EventNotificatorEntity> listOfSavedEntities = eventNotificatorRepository.saveAll(eventNotificatorEntities);
        log.info("Saved {} notifications", listOfSavedEntities.size());
    }
}
