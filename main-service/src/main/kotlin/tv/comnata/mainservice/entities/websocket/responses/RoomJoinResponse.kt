package tv.comnata.mainservice.entities.websocket.responses

import java.time.LocalDateTime

data class RoomJoinResponse(
    val newUserId: String,
    val allUserIds: List<String>,
    val dateTime: LocalDateTime,
) : Response(NotificationType.JOIN)