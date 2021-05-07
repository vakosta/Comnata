package tv.comnata.mainservice.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tv.comnata.mainservice.entities.Room
import tv.comnata.mainservice.entities.User
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
        val room = roomRepository.findRoomByName(roomId)
        val user = User(userId, room!!)
        userRepository.save(user)
        val users = userRepository.findAllByRoomName(roomId).map { it.username }

        websocketService.send(
            URL_ROOM_JOINS.format(roomId),
            RoomJoinResponse(userId, users, LocalDateTime.now())
        )
    }

    fun processVideoLeft(userId: String) {
        val user = userRepository.findUserByUsername(userId)
        val room = roomRepository.findRoomByName(user.room.name)
        userRepository.delete(user)

        val users = userRepository.findAllByRoomName(room!!.name).map { it.username }
        websocketService.send(
            URL_ROOM_LEFTS.format(room.name),
            RoomLeftResponse(userId, users, LocalDateTime.now())
        )
    }

    fun processRoomVideoAction(userId: String, roomId: String, seekTime: Double, actionType: ActionType) {
        websocketService.send(
            URL_ROOM_ACTIONS.format(roomId),
            RoomActionResponse(userId, seekTime, actionType, LocalDateTime.now())
        )
    }

    fun processRoomChatMessage(userId: String, roomId: String, text: String) {
        websocketService.send(
            URL_ROOM_CHAT_MESSAGES.format(roomId),
            RoomChatMessageResponse(userId, text, LocalDateTime.now())
        )
    }

    fun processRoomReaction(userId: String, roomId: String, reaction: Reaction) {
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