package com.projetpi.cloudup.repository;


import com.projetpi.cloudup.entities.PrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {

        PrivateChat findFirstByOrderByIdAsc();

}
