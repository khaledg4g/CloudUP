package com.projetpi.cloudup.RestController;


import com.projetpi.cloudup.dto.MessageRequest;
import com.projetpi.cloudup.entities.Message;
import com.projetpi.cloudup.entities.PrivateChat;
import com.projetpi.cloudup.entities.Reclamation;
import com.projetpi.cloudup.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MessageController {


    private final MessageService messageService;

    public MessageController(MessageService messageService) {

        this.messageService = messageService;
    }





    @PostMapping("/send-private-message/{id}")
    public void sendPrivateMessage(@PathVariable final String id,
                                   @RequestBody final MessageRequest message) {
         messageService.sendMessage(message,id);
    }
    @GetMapping("/get-private-chat/{id}")
    public PrivateChat getPrivateChat(@PathVariable final String id) {
       return  messageService.getPrivateChat(id);
    }

    @PostMapping("/send-admin-private-message/{id}")
    public void sendAdminPrivateMessage(@PathVariable final String id,
                                   @RequestBody final MessageRequest message) {
        messageService.sendAdminMessage(message,id);
    }
    @GetMapping("/getadmin-private-chat")
    public List<PrivateChat> getPrivateChat() {
        return  messageService.getAdminPrivateChat();
    }
}
