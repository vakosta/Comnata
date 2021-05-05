package tv.comnata.mainservice.entities.websocket.responses

import tv.comnata.mainservice.entities.websocket.Reaction
import java.time.LocalDateTime

data class RoomReactionResponse(
    val author: String,
    val type: Reaction,
    val actionTime: LocalDateTime,
) : Response(NotificationType.REACTION)