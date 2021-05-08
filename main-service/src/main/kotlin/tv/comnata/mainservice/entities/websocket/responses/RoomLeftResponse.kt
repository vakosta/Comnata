package tv.comnata.mainservice.entities.websocket.responses

import java.time.LocalDateTime

data class RoomLeftResponse(
    val leftUserId: String,
    val remainingUserIds: List<String>,
    val dateTime: LocalDateTime
) : Response(NotificationType.LEFT)