package tv.comnata.mainservice.controllers

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tv.comnata.mainservice.entities.websocket.getActionType
import tv.comnata.mainservice.entities.websocket.getReaction
import tv.comnata.mainservice.entities.websocket.requests.RoomActionRequest
import tv.comnata.mainservice.entities.websocket.requests.RoomChatMessageRequest
import tv.comnata.mainservice.entities.websocket.requests.RoomReactionRequest
import tv.comnata.mainservice.services.RoomService
import java.security.Principal

@Controller
class WebsocketController(
    @Autowired
    private val roomService: RoomService,
) {
    @RequestMapping(URL_ROOM_JOIN, method = [RequestMethod.POST])
    @MessageMapping(URL_ROOM_JOIN)
    fun processRoomJoin(
        principal: Principal,
        @DestinationVariable roomId: String,
    ) {
        logger.info("JOIN")
        roomService.processVideoJoin(principal.name, roomId)
    }

    @RequestMapping(URL_ROOM_VIDEO_ACTION, method = [RequestMethod.POST])
    @MessageMapping(URL_ROOM_VIDEO_ACTION)
    fun processRoomVideoAction(
        principal: Principal,
        @DestinationVariable roomId: String,
        @Payload request: RoomActionRequest
    ) {
        logger.info("VIDEO ACTION \t ${request.type}")
        roomService.processRoomVideoAction(principal.name, roomId, request.seekTime!!, request.type!!.getActionType())
    }

    @RequestMapping(URL_ROOM_CHAT_MESSAGE, method = [RequestMethod.POST])
    @MessageMapping(URL_ROOM_CHAT_MESSAGE)
    fun processRoomChatMessage(
        principal: Principal,
        @DestinationVariable roomId: String,
        @Payload request: RoomChatMessageRequest
    ) {
        logger.info("CHAT MESSAGE")
        roomService.processRoomChatMessage(principal.name, roomId, request.text!!)
    }

    @RequestMapping(URL_ROOM_REACTION, method = [RequestMethod.POST])
    @MessageMapping(URL_ROOM_REACTION)
    fun processRoomReaction(
        principal: Principal,
        @DestinationVariable roomId: String,
        @Payload request: RoomReactionRequest
    ) {
        logger.info("REACTION")
        roomService.processRoomReaction(principal.name, roomId, request.reaction!!.getReaction())
    }

    companion object {
        private val logger = LoggerFactory.getLogger(WebsocketController::class.java)

        const val URL_ROOM_JOIN = "/room/{roomId}/join"
        const val URL_ROOM_VIDEO_ACTION = "/room/{roomId}/videoAction"
        const val URL_ROOM_CHAT_MESSAGE = "/room/{roomId}/chatMessage"
        const val URL_ROOM_REACTION = "/room/{roomId}/reaction"
    }
}