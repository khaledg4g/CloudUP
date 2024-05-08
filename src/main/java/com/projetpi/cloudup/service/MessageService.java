package com.projetpi.cloudup.service;


import com.projetpi.cloudup.dto.MessageRequest;
import com.projetpi.cloudup.entities.Message;
import com.projetpi.cloudup.entities.PrivateChat;
import com.projetpi.cloudup.entities.Role;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.MessageRepository;
import com.projetpi.cloudup.repository.PrivateChatRepository;
import com.projetpi.cloudup.repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final PrivateChatRepository privateChatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;



    public MessageService(MessageRepository messageRepository, PrivateChatRepository privateChatRepository, SimpMessagingTemplate messagingTemplate, UserDetailsService userDetailsService, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.privateChatRepository = privateChatRepository;
        this.messagingTemplate = messagingTemplate;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    public void sendMessage(MessageRequest messageRequest,Long senderId) {
        User user = getuserconneted();
        User sender = userRepository.findById(senderId).orElse(null);
        Message message = new Message();
        message.setTimestamp(LocalDateTime.now());
        message.setContent(messageRequest.getMessage());
        message.setSenderId(user.getIdUser());
        if (user.getRoles().equals(Role.Admin)) {
            List<PrivateChat> privateChat = privateChatRepository.findAllByCreatorEquals(sender);
            message.setPrivateChat(privateChat.getFirst());
            messageRepository.save(message);
            messagingTemplate.convertAndSend("/topic/" + senderId + "/.chat", messageRequest);
        } else {
            List<PrivateChat> privateChat = privateChatRepository.findAllByCreatorEquals(user);
            if (privateChat.isEmpty())
                message.setPrivateChat(privateChatRepository.save(new PrivateChat(user)));
            else
                message.setPrivateChat(privateChat.getFirst());

            messageRepository.save(message);
            for (User admin : userRepository.findAll()) {
                if (admin.getRoles().equals(Role.Admin)) {
                    messagingTemplate.convertAndSend("/topic/" + admin.getIdUser() + "/.chat", messageRequest);
                }
            }


        }
    }

    public void readMSg(Long id){
        PrivateChat privateChat = privateChatRepository.findById(id).orElse(null);

        for(Message message : privateChat.getMessages()){
            message.setVu(true);
            messageRepository.save(message);
        }


    }
    public List<PrivateChat> getPrivateChat() {
        User user = getuserconneted();
        if (user.getRoles().equals(Role.Admin))
           return privateChatRepository.findAll();
       return privateChatRepository.findAllByCreatorEquals(this.getuserconneted());
    }




    public User getuserconneted(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
          return (User) userDetailsService.loadUserByUsername(username);
            // Do something with the username or other user details
        } else {
            // User is not authenticated
            return null;
        }
    }
}
