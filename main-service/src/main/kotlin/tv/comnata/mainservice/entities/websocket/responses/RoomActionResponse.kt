package tv.comnata.mainservice.entities.websocket.responses

import tv.comnata.mainservice.entities.websocket.ActionStep
import tv.comnata.mainservice.entities.websocket.ActionType
import java.time.LocalDateTime

data class RoomActionResponse(
    val actionId: Long,
    val author: String,
    val seekTime: Double,
    val type: ActionType,
    val step: ActionStep,
    val actionTime: LocalDateTime,
) : Response(NotificationType.VIDEO_ACTION)
