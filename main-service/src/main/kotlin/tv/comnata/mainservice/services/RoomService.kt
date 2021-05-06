package tv.comnata.mainservice.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tv.comnata.mainservice.entities.Room
import tv.comnata.mainservice.entities.websocket.ActionType
import tv.comnata.mainservice.entities.websocket.Reaction
import tv.comnata.mainservice.entities.websocket.responses.*
import tv.comnata.mainservice.repositories.RoomRepository
import tv.comnata.mainservice.repositories.UserRepository
import java.time.LocalDateTime

@Service
class RoomService(
    @Autowired
    private val websocketService: WebsocketService,

    @Autowired
    private val userRepository: UserRepository,

    @Autowired
    private val roomRepository: RoomRepository,
) {
    fun createRoom(roomName: String) {
        val room = Room(
            roomName,
            LocalDateTime.now(),
        )

        roomRepository.saveAndFlush(room)
    }

    fun processVideoJoin(userId: String, roomId: String) {
        websocketService.send(
            URL_ROOM_JOINS.format(roomId),
            RoomJoinResponse(userId, LocalDateTime.now())
        )
    }

    fun processVideoLeft(userId: String, roomId: String) {
        websocketService.send(
            URL_ROOM_LEFTS.format(roomId),
            RoomLeftResponse(userId, LocalDateTime.now())
        )
    }

    fun processRoomVideoAction(userId: String, roomId: Int, seekTime: Double, actionType: ActionType) {
        websocketService.send(
            URL_ROOM_ACTIONS.format(roomId),
            RoomActionResponse(userId, seekTime, actionType, LocalDateTime.now())
        )
    }

    fun processRoomChatMessage(userId: String, roomId: Int, text: String) {
        websocketService.send(
            URL_ROOM_CHAT_MESSAGES.format(roomId),
            RoomChatMessageResponse(userId, text, LocalDateTime.now())
        )
    }

    fun processRoomReaction(userId: String, roomId: Int, reaction: Reaction) {
        websocketService.send(
            URL_ROOM_REACTIONS.format(roomId),
            RoomReactionResponse(userId, reaction, LocalDateTime.now())
        )
    }

    companion object {
        private const val URL_ROOM_JOINS = "/topic/room/%s/joins"
        private const val URL_ROOM_LEFTS = "/topic/room/%s/lefts"
        private const val URL_ROOM_ACTIONS = "/topic/room/%s/videoActions"
        private const val URL_ROOM_CHAT_MESSAGES = "/topic/room/%s/chatMessages"
        private const val URL_ROOM_REACTIONS = "/topic/room/%s/reactions"
    }
}