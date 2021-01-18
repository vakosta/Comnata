package tv.comnata.websocketserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import tv.comnata.websocketserver.entities.AppMessage;
import tv.comnata.websocketserver.entities.AppNotification;
import tv.comnata.websocketserver.entities.RoomAction;

@Controller
public class ChatController {
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/app")
    public void processAppMessage(@Payload AppMessage message) {
        messagingTemplate.convertAndSendToUser(
                "/topic/app",
                "/topic/app",
                new AppNotification("123", "321", "kek")
        );
    }

    @MessageMapping("/app/room/{roomId}/action")
    public void processRoomAction(@PathVariable(value = "roomId") String roomId, @Payload RoomAction action) {
        messagingTemplate.convertAndSend(
                "/topic/room/" + roomId,
                new AppNotification("123", "321", "kek")
        );
    }

    @MessageMapping("/app/room/{roomId}/message")
    public void processRoomMessage(@PathVariable(value = "roomId") String roomId, @Payload RoomAction action) {
        messagingTemplate.convertAndSend(
                "/topic/room/" + roomId,
                new AppNotification("123", "321", "kek")
        );
    }
}
