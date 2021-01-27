package tv.comnata.websocketserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import tv.comnata.websocketserver.entities.AppMessage;
import tv.comnata.websocketserver.entities.AppNotification;
import tv.comnata.websocketserver.entities.RoomMessageChat;
import tv.comnata.websocketserver.entities.RoomMessageVideo;

import java.security.Principal;

@Controller
public class ChatController {
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/base")
    public void processAppMessage(Principal principal, @Payload AppMessage message) {
        messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/topic/base",
                new AppNotification("123", "321", "kek")
        );
    }

    @MessageMapping("/room/{roomId}/videoAction")
    public void processRoomVideoAction(@PathVariable(value = "roomId") String roomId, @Payload RoomMessageVideo action) {
        messagingTemplate.convertAndSend(
                "/topic/room/" + roomId,
                new AppNotification("123", "321", "kek")
        );
    }

    @MessageMapping("/room/{roomId}/chatMessage")
    public void processRoomChatMessage(@PathVariable(value = "roomId") String roomId,
                                       @Payload RoomMessageChat chatMessage) {
        messagingTemplate.convertAndSend(
                "/topic/room/" + roomId,
                new AppNotification("123", "321", "kek")
        );
    }

    @MessageMapping("/test")
    public void processTest(@Payload AppMessage message) {
        messagingTemplate.convertAndSend(
                "/topic/test",
                new AppNotification("123", "Public message", message.getName())
        );
    }

    @MessageMapping("/testUser")
    public void processTestUser(Principal principal, @Payload AppMessage message) {
        messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/topic/test",
                new AppNotification("123", "Personal message", message.getName())
        );
    }
}
