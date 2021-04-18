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
import tv.comnata.mainservice.entities.websocket.requests.RoomActionRequest
import tv.comnata.mainservice.entities.websocket.requests.RoomChatMessageRequest
import tv.comnata.mainservice.services.RoomService
import java.security.Principal

@Controller
class WebsocketController(
    @Autowired
    private val messagingTemplate: SimpMessagingTemplate,

    @Autowired
    private val roomService: RoomService,
) {
    @MessageMapping("/base")
    fun processAppMessage(principal: Principal, @Payload message: AppMessage) {
        messagingTemplate.convertAndSendToUser(
            principal.name,
            "/topic/base",
            AppNotification("123", "321", "kek")
        )
    }

    @MessageMapping("/room/{roomId}/videoAction")
    fun processRoomVideoAction(
        principal: Principal,
        @PathVariable(value = "roomId") roomId: Int,
        @Payload action: RoomActionRequest
    ) {
        roomService.processRoomVideoAction(principal.name, roomId, action)
    }

    @MessageMapping("/room/{roomId}/chatMessage")
    fun processRoomChatMessage(
        principal: Principal,
        @DestinationVariable roomId: Int,
        @Payload chatMessage: RoomChatMessageRequest
    ) {
        roomService.processRoomChatMessage(principal.name, roomId, chatMessage)
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