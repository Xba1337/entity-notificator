package ru.spring.kafka.eventnotificator.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventNotificatorRepository extends JpaRepository<EventNotificatorEntity, Integer> {

    @Modifying
    @Query(value = """
            DELETE FROM EventNotificatorEntity e
            WHERE e.createdAt <= :weekAgo
            """)
    int deleteEventNotificatorEntitiesWithCreationDateOlderThanOneWeek(
            @Param("weekAgo") LocalDateTime weekAgo
    );

    @Modifying
    @Query(value = """
            UPDATE EventNotificatorEntity e
            SET e.read = true
            WHERE e.userLogin = :userLogin
            AND e.id in :ids
            """)
    int markNotificationsAsRead(
            @Param("userLogin") String userLogin,
            @Param("ids") List<Integer> ids
    );

    List<EventNotificatorEntity> findAll();
}
