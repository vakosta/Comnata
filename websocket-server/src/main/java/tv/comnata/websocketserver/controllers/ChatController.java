package tv.comnata.websocketserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import tv.comnata.websocketserver.entities.ChatNotification;

@Controller
public class ChatController {
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    public void processMessage() throws InterruptedException {
        messagingTemplate.convertAndSend(
                "/topic/messages",
                new ChatNotification("123", "321", "kek"));
    }
}
