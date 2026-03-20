package ru.spring.kafka.eventnotificator.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class EventNotificatorScheduler {

    private static final Logger log = LoggerFactory.getLogger(EventNotificatorScheduler.class);

    private final EventNotificatorRepository eventNotificatorRepository;

    public EventNotificatorScheduler(EventNotificatorRepository eventNotificatorRepository) {
        this.eventNotificatorRepository = eventNotificatorRepository;
    }

    @Scheduled(cron = "${scheduler.event.status.cron}")
    @Transactional
    public void deleteNotificationsOlderThanWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekAgo = now.minusDays(7);

        int numOfDeletedNotifications = eventNotificatorRepository
                .deleteEventNotificatorEntitiesWithCreationDateOlderThanOneWeek(weekAgo);

        if (numOfDeletedNotifications > 0) {
            log.info("Deleted {} notifications", numOfDeletedNotifications);
        }

        else log.info("There are no notifications older than week");
    }
}
