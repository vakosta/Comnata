package tv.comnata.mainservice.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import tv.comnata.mainservice.entities.websocket.requests.RoomActionRequest
import tv.comnata.mainservice.entities.websocket.requests.RoomChatMessageRequest
import tv.comnata.mainservice.entities.websocket.responses.RoomActionResponse
import tv.comnata.mainservice.entities.websocket.responses.RoomChatMessageResponse
import tv.comnata.mainservice.entities.websocket.responses.getAction
import java.time.LocalDateTime

@Service
class RoomService(
    @Autowired
    private val messagingTemplate: SimpMessagingTemplate,
) {
    fun processRoomVideoAction(userId: String, roomId: Int, action: RoomActionRequest) {
        messagingTemplate.convertAndSend(
            URL_ROOM_ACTIONS.format(roomId),
            RoomActionResponse(userId, action.seekTime!!, LocalDateTime.now(), action.type!!.getAction())
        )
    }

    fun processRoomChatMessage(userId: String, roomId: Int, action: RoomChatMessageRequest) {
        messagingTemplate.convertAndSend(
            URL_ROOM_MESSAGES.format(roomId),
            RoomChatMessageResponse(userId, action.text!!, LocalDateTime.now())
        )
    }

    companion object {
        private const val URL_ROOM_ACTIONS = "/topic/room/%s/actions"
        private const val URL_ROOM_MESSAGES = "/topic/room/%s/messages"
    }
}