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
@RequestMapping("/reclamation")
@CrossOrigin(origins = "*")
public class MessageController {


    private final MessageService messageService;

    public MessageController(MessageService messageService) {

        this.messageService = messageService;
    }





    @PostMapping("/send-private-message/{id}")
    public void sendPrivateMessage(@PathVariable final Long id,
                                   @RequestBody final MessageRequest message) {
         messageService.sendMessage(message,id);
    }
    @GetMapping("/get-private-chat")
    public List<PrivateChat> getPrivateChat() {
       return  messageService.getPrivateChat();
    }

    @GetMapping("/vu-message/{id}")
    public void sendPrivateMessage(@PathVariable final Long id) {
        messageService.readMSg(id);
    }

}
