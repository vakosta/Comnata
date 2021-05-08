package tv.comnata.mainservice.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tv.comnata.mainservice.entities.Action
import tv.comnata.mainservice.entities.Room
import tv.comnata.mainservice.entities.User
import tv.comnata.mainservice.entities.websocket.ActionStep
import tv.comnata.mainservice.entities.websocket.ActionType
import tv.comnata.mainservice.entities.websocket.Reaction
import tv.comnata.mainservice.entities.websocket.getActionType
import tv.comnata.mainservice.entities.websocket.responses.*
import tv.comnata.mainservice.repositories.RoomRepository
import tv.comnata.mainservice.repositories.UserRepository
import tv.comnata.mainservice.stores.ActionsStore
import java.time.LocalDateTime

@Service
class RoomService(
    @Autowired private val websocketService: WebsocketService,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val roomRepository: RoomRepository,
    @Autowired private val actionsStore: ActionsStore,
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

    @Transactional
    fun processRoomVideoAction(userId: String, roomId: String, seekTime: Double, type: ActionType) {
        if (type == ActionType.SEEK) {
            processRoomVideoActionSeek(userId, roomId, seekTime, type)
        } else {
            websocketService.send(
                URL_ROOM_ACTIONS.format(roomId),
                RoomActionResponse(-1, userId, seekTime, type, ActionStep.READY, LocalDateTime.now())
            )
        }
    }

    @Transactional
    fun processRoomVideoActionSeek(userId: String, roomId: String, seekTime: Double, type: ActionType) {
        val room = roomRepository.findRoomByName(roomId)
        val action = Action(actionsStore.index, type.name, seekTime, userId)
        action.addUsers(room!!.users.map { it.username }.toMutableSet())
        actionsStore.waitingActions[action.id] = action

        websocketService.send(
            URL_ROOM_ACTIONS.format(roomId),
            RoomActionResponse(action.id, userId, seekTime, type, ActionStep.CHECK, LocalDateTime.now())
        )
    }

    fun processRoomVideoActionReady(userId: String, roomId: String, actionId: Long) {
        val action = actionsStore.waitingActions[actionId]
        action!!.deleteUser(userId)

        if (action.users.isEmpty()) {
            websocketService.send(
                URL_ROOM_ACTIONS.format(roomId),
                RoomActionResponse(
                    actionId,
                    userId,
                    action.seekTime,
                    action.type.getActionType(),
                    ActionStep.READY,
                    LocalDateTime.now()
                )
            )
            actionsStore.waitingActions.remove(actionId)
        }
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
