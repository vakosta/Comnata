package tv.comnata.mainservice.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import tv.comnata.mainservice.entities.websocket.AppMessage
import tv.comnata.mainservice.entities.websocket.AppNotification
import tv.comnata.mainservice.entities.websocket.RoomMessageVideo
import java.security.Principal

@Controller
class WebsocketController(
    @Autowired
    private val messagingTemplate: SimpMessagingTemplate
) {
    @MessageMapping("/base")
    fun processAppMessage(principal: Principal, @Payload message: AppMessage?) {
        messagingTemplate.convertAndSendToUser(
            principal.name,
            "/topic/base",
            AppNotification("123", "321", "kek")
        )
    }

    @MessageMapping("/room/{roomId}/videoAction")
    fun processRoomVideoAction(
        @PathVariable(value = "roomId") roomId: String,
        @Payload action: RoomMessageVideo?
    ) {
        messagingTemplate.convertAndSend(
            "/topic/room/$roomId",
            AppNotification("123", "321", "kek")
        )
    }

    @MessageMapping("/room/{roomId}/chatMessage")
    fun processRoomChatMessage(
        @DestinationVariable roomId: String,
        @Payload chatMessage: AppMessage
    ) {
        messagingTemplate.convertAndSend(
            "/topic/room/$roomId",
            AppNotification("123", "321", "kek")
        )
    }

    @MessageMapping("/test")
    fun processTest(@Payload message: AppMessage) {
        messagingTemplate.convertAndSend(
            "/topic/test",
            AppNotification("123", "Public message", message.name)
        )
    }

    @MessageMapping("/testUser")
    fun processTestUser(principal: Principal, @Payload message: AppMessage) {
        messagingTemplate.convertAndSendToUser(
            principal.name, "/topic/test",
            "userClient.test()"
        )
    }
}