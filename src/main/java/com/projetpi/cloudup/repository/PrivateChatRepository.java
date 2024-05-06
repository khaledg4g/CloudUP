package com.projetpi.cloudup.repository;


import com.projetpi.cloudup.entities.PrivateChat;
import com.projetpi.cloudup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {

        List<PrivateChat> findAllByCreatorEquals(User user);

}
