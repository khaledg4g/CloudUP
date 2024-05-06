package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.TimeSlot;
import com.projetpi.cloudup.entities.TimeSlotRequest;
import com.projetpi.cloudup.entities.TimeSlotResponse;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.TimeSlotRepository;
import com.projetpi.cloudup.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class SlotsService {
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;
    private final SlotsMapper slotsMapper;

    public Long saveSlot(TimeSlotRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        TimeSlot savedTimeSlot = slotsMapper.toTimeSlot(request);
        savedTimeSlot.setUser(user);
        return timeSlotRepository.save(savedTimeSlot).getId();
    }

    public void deleteSlot(long id, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        TimeSlot timeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No TimeSlot found"));
        if (!Objects.equals(timeSlot.getUser().getIdUser(), user.getIdUser())) {
            throw new EntityNotFoundException("NO PERMISSION TO DELETE TimeSlots");
        }
        timeSlotRepository.deleteById(id);
    }


    /*   To be implemented on Booking component  */
    public List<TimeSlotResponse> getSlotsForBooking(Long userID, String day) {
        User user = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("No User found"));
        List<TimeSlot> slots = timeSlotRepository.findByUserAndDayOfWeek(user, day);
        List<TimeSlotResponse> responses = slots.stream().map(slotsMapper::toTimeSlotResponse).toList();
        return responses;
    }


    /*   To be implemented on scheduling component  */
    public List<TimeSlotResponse> getSlotsOfConnectedUserOnly(String day, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        List<TimeSlot> slots = timeSlotRepository.findByDayOfWeekAndUser_IdUser(day, user.getIdUser());
        List<TimeSlotResponse> responses = slots.stream().map(slotsMapper::toTimeSlotResponse).toList();
        return responses;
    }


}
