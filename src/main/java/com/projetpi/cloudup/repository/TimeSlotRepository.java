package com.projetpi.cloudup.repository;
import com.projetpi.cloudup.entities.TimeSlot;
import com.projetpi.cloudup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByDayOfWeekAndUser_IdUser(String dayOfWeek, long userId);
    List<TimeSlot> findByUserAndDayOfWeek(User user, String day);

}
