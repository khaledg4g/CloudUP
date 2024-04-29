package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.TimeSlot;
import com.projetpi.cloudup.entities.TimeSlotRequest;
import com.projetpi.cloudup.entities.TimeSlotResponse;
import org.springframework.stereotype.Service;

@Service
public class SlotsMapper {
    public TimeSlot toTimeSlot(TimeSlotRequest request) {
        return new TimeSlot().builder()
                .id(request.id())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .dayOfWeek(request.dayOfWeek())
                .build();
    }

    public TimeSlotResponse toTimeSlotResponse(TimeSlot timeSlot) {
        return TimeSlotResponse.builder()
                .id(timeSlot.getId())
                .startTime(timeSlot.getStartTime())
                .endTime(timeSlot.getEndTime())
                .dayOfWeek(timeSlot.getDayOfWeek())
                .idUser(timeSlot.getUser().getIdUser())
                .build();
    }
}
