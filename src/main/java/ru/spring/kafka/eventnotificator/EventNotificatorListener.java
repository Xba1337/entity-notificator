package ru.spring.kafka.eventnotificator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.spring.kafka.eventnotificator.notification.EventChangeMessage;
import ru.spring.kafka.eventnotificator.notification.EventNotificatorService;

@Component
public class EventNotificatorListener {

    private static final Logger log = LoggerFactory.getLogger(EventNotificatorListener.class);

    private final EventNotificatorService eventNotificatorService;

    public EventNotificatorListener(EventNotificatorService eventNotificatorService) {
        this.eventNotificatorService = eventNotificatorService;
    }

    @KafkaListener(topics = "events-notification", containerFactory = "containerFactory")
    public void listen(EventChangeMessage record) {
        log.info("Received record: {}", record);

        eventNotificatorService.saveNotification(record);


    }
}
