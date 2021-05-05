package tv.comnata.mainservice.entities.websocket.responses

import java.time.LocalDateTime

data class RoomJoinResponse(
    val userId: String,
    val dateTime: LocalDateTime
) : Response(NotificationType.JOIN)