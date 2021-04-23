package tv.comnata.mainservice.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
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
    @RequestMapping(URL_BASE, method = [RequestMethod.POST])
    @MessageMapping(URL_BASE)
    fun processAppMessage(principal: Principal, @Payload message: AppMessage) {
        messagingTemplate.convertAndSendToUser(
            principal.name,
            "/topic/base",
            AppNotification("123", "321", "kek")
        )
    }

    @RequestMapping(URL_ROOM_VIDEO_ACTION, method = [RequestMethod.POST])
    @MessageMapping(URL_ROOM_VIDEO_ACTION)
    fun processRoomVideoAction(
        principal: Principal,
        @PathVariable(value = "roomId") roomId: Int,
        @Payload action: RoomActionRequest
    ) {
        roomService.processRoomVideoAction(principal.name, roomId, action)
    }

    @RequestMapping(URL_ROOM_CHAT_MESSAGE, method = [RequestMethod.POST])
    @MessageMapping(URL_ROOM_CHAT_MESSAGE)
    fun processRoomChatMessage(
        principal: Principal,
        @DestinationVariable roomId: Int,
        @Payload chatMessage: RoomChatMessageRequest
    ) {
        roomService.processRoomChatMessage(principal.name, roomId, chatMessage)
    }

    @RequestMapping(URL_TEST, method = [RequestMethod.POST])
    @MessageMapping(URL_TEST)
    fun processTest(@Payload message: AppMessage) {
        messagingTemplate.convertAndSend(
            "/topic/test",
            AppNotification("123", "Public message", message.name)
        )
    }

    @RequestMapping(URL_TEST_USER, method = [RequestMethod.POST])
    @MessageMapping(URL_TEST_USER)
    fun processTestUser(principal: Principal, @Payload message: AppMessage) {
        messagingTemplate.convertAndSendToUser(
            principal.name, "/topic/test",
            "userClient.test()"
        )
    }

    companion object {
        const val URL_BASE = "/base"
        const val URL_ROOM_VIDEO_ACTION = "/room/{roomId}/videoAction"
        const val URL_ROOM_CHAT_MESSAGE = "/room/{roomId}/chatMessage"
        const val URL_TEST = "/test"
        const val URL_TEST_USER = "/testUser"
    }
}