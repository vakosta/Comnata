package tv.comnata.mainservice.entities.websocket.responses

import tv.comnata.mainservice.entities.websocket.ActionType
import java.time.LocalDateTime

data class RoomActionResponse(
    val actor: String,
    val seekTime: Int,
    val actionTime: LocalDateTime,
    val type: ActionType,
) : Response(NotificationType.ACTION)