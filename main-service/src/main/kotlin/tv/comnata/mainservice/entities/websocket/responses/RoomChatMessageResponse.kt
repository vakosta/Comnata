package tv.comnata.mainservice.entities.websocket.responses

import java.time.LocalDateTime

data class RoomChatMessageResponse(
    val userId: String,
    val text: String,
    val dateTime: LocalDateTime,
) : Response(NotificationType.CHAT_MESSAGE)