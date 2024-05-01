package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.repository.EvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class EventScheduler {
    @Autowired
    private EvenementServiceIMP eventService;
    @Autowired
    EvenementRepository eventRepository;
    @Scheduled(fixedRate = 60000) // Schedule every hour (1 hour in milliseconds)
    public void checkReportedEvents() {
        List<Evenement> events = eventRepository.findAllByReportsGreaterThan(5);
        for (Evenement event : events) {
            // Ban and delete the event (implementation depends on your logic)
            eventRepository.delete(event);
            // Send notification or log event deletion
        }
    }
}
