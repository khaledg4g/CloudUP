package com.projetpi.cloudup.service;


import com.projetpi.cloudup.dto.MessageRequest;
import com.projetpi.cloudup.entities.Message;
import com.projetpi.cloudup.entities.PrivateChat;
import com.projetpi.cloudup.repository.MessageRepository;
import com.projetpi.cloudup.repository.PrivateChatRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final PrivateChatRepository privateChatRepository;
    private final SimpMessagingTemplate messagingTemplate;


    public MessageService(MessageRepository messageRepository, PrivateChatRepository privateChatRepository, SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.privateChatRepository = privateChatRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(MessageRequest messageRequest,String userid) {
        Message message = new Message();
        message.setTimestamp(LocalDateTime.now());
        message.setContent(messageRequest.getMessage());
        message.setSenderId(userid);
        PrivateChat privateChat =privateChatRepository.findAllByCreatorEquals(userid);
        if(privateChat==null) privateChat =privateChatRepository.save(new PrivateChat(userid));
        message.setPrivateChat(privateChat);
        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/admin/.chat",messageRequest);
    }


    public PrivateChat getPrivateChat(String id) {
       return privateChatRepository.findAllByCreatorEquals(id);
    }



    public void sendAdminMessage(MessageRequest messageRequest, String userid) {
        Message message = new Message();
        message.setTimestamp(LocalDateTime.now());
        message.setContent(messageRequest.getMessage());
        message.setSenderId("admin");
        PrivateChat privateChat =privateChatRepository.findAllByCreatorEquals(userid);
        message.setPrivateChat(privateChat);
        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/"+userid+"/.chat",messageRequest);
    }

    public List<PrivateChat> getAdminPrivateChat() {
        return privateChatRepository.findAll();
    }
}
