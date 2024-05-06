package com.projetpi.cloudup.entities;
import java.time.LocalDateTime;

public record TimeSlotRequest(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String dayOfWeek
) {
}
