package tv.comnata.mainservice.services

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tv.comnata.mainservice.entities.db.Action
import tv.comnata.mainservice.entities.db.Room
import tv.comnata.mainservice.entities.db.User
import tv.comnata.mainservice.entities.websocket.ActionStep
import tv.comnata.mainservice.entities.websocket.ActionType
import tv.comnata.mainservice.entities.websocket.Reaction
import tv.comnata.mainservice.entities.websocket.getActionType
import tv.comnata.mainservice.entities.websocket.responses.*
import tv.comnata.mainservice.repositories.ActionRepository
import tv.comnata.mainservice.repositories.RoomRepository
import tv.comnata.mainservice.repositories.UserRepository
import java.time.LocalDateTime

@Service
class RoomService(
    @Autowired private val roomRepository: RoomRepository,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val actionRepository: ActionRepository,
    @Autowired private val websocketService: WebsocketService,
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

    @Transactional
    fun processVideoLeft(userId: String) {
        val user = userRepository.findUserByUsername(userId)
        val room = roomRepository.findRoomByName(user.room.name)
        user.deleteAllActions()
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
        val user = userRepository.findUserByUsername(userId)
        val action = Action(type.name, seekTime, ActionStep.CHECK.name, room!!, user)
        action.addUsers(room.users)
        actionRepository.saveAndFlush(action)

        websocketService.send(
            URL_ROOM_ACTIONS.format(roomId),
            RoomActionResponse(action.id!!, userId, seekTime, type, ActionStep.CHECK, LocalDateTime.now())
        )
    }

    @Transactional
    fun processRoomVideoActionReady(userId: String, actionId: Long) {
        val user = userRepository.findUserByUsername(userId)
        val action = actionRepository.findByIdOrNull(actionId)
        action!!.deleteUser(user)
        actionRepository.saveAndFlush(action)

        val users = userRepository.findAllByActionsContains(action)
        if (users.isEmpty()) {
            actionRepository.delete(action)

            websocketService.send(
                URL_ROOM_ACTIONS.format(user.room.name),
                RoomActionResponse(
                    action.id!!,
                    userId,
                    action.seekTime,
                    action.type.getActionType(),
                    ActionStep.READY,
                    LocalDateTime.now()
                )
            )
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
        private val logger = LoggerFactory.getLogger(RoomService::class.java)

        private const val URL_ROOM_JOINS = "/topic/room/%s/joins"
        private const val URL_ROOM_LEFTS = "/topic/room/%s/lefts"
        private const val URL_ROOM_ACTIONS = "/topic/room/%s/videoActions"
        private const val URL_ROOM_CHAT_MESSAGES = "/topic/room/%s/chatMessages"
        private const val URL_ROOM_REACTIONS = "/topic/room/%s/reactions"
    }
}