package com.projetpi.cloudup.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private Long senderId;
    private Boolean vu=false;

    @ManyToOne
    @JoinColumn(name = "private_chat_id")
    @JsonIgnore
    private PrivateChat privateChat;
}
