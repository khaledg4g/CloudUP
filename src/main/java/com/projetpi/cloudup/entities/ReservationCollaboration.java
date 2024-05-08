package com.projetpi.cloudup.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class ReservationCollaboration implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id") // This is the foreign key column in the reservation table
    private User user;

    @ManyToOne
    @JoinColumn(name = "collaboration_id") // This is the foreign key column in the reservation table
    private Collaboration collaboration;
}
