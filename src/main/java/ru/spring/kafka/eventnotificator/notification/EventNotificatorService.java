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
    public void markAllNotificationsAsRead(String login) {
        log.info("Marking notification");

        List<EventNotificatorEntity> eventNotificatorEntities = eventNotificatorRepository.findAll();

        List<EventNotificatorEntity> notifications = eventNotificatorEntities.stream()
                .filter(n -> n.getParticipants() != null && n.getParticipants().contains(login))
                .peek(n -> n.setRead(true))
                .toList();

        eventNotificatorRepository.saveAll(notifications);

        log.info("Found {} notifications", notifications.size());

    }

    public void saveNotification(EventChangeMessage notification){
        log.info("Saving notification");

        EventNotificatorEntity eventNotificatorEntity =
                eventNotificatorMapper.toEntity(notification);

        eventNotificatorRepository.save(eventNotificatorEntity);
        log.info("Notification saved with id {}", eventNotificatorEntity.getId());
    }
}
