package com.projetpi.cloudup.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record ReservationRequest(
        long idR,
        Date dateR,
        Etat statusR
) {
}
