package tv.comnata.mainservice.entities.websocket.requests

import java.time.LocalDateTime

data class RoomActionRequest(
    val seekTime: Int,
    val actionTime: LocalDateTime,
    val type: String,
)