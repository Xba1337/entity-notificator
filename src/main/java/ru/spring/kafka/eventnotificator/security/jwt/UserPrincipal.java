package ru.spring.kafka.eventnotificator.security.jwt;

public record UserPrincipal(
        Integer id,
        String role
) {
}
