package com.projetpi.cloudup.repository;


import com.projetpi.cloudup.entities.PrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {

        PrivateChat findAllByCreatorEquals(String userId);

}
