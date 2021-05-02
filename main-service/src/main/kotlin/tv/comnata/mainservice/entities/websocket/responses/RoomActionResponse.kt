package tv.comnata.mainservice.entities.websocket.responses

import java.time.LocalDateTime

data class RoomActionResponse(
    val actor: String,
    val seekTime: Long,
    val actionTime: LocalDateTime,
    val type: ActionType,
) : Response(NotificationType.ACTION) {
    enum class ActionType {
        RESUME,
        PAUSE,
        SEEK;
    }
}

fun String.getAction(): RoomActionResponse.ActionType {
    return when (this.toUpperCase()) {
        "RESUME" -> RoomActionResponse.ActionType.RESUME
        "PAUSE" -> RoomActionResponse.ActionType.PAUSE
        else -> RoomActionResponse.ActionType.SEEK
    }
}