package tv.comnata.mainservice.entities.websocket.responses

import java.time.LocalDateTime

data class RoomLeftResponse(
    val userId: String,
    val allUserIds: List<String>,
    val dateTime: LocalDateTime
) : Response(NotificationType.LEFT)