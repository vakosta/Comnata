package tv.comnata.mainservice.entities.websocket

enum class ActionType {
    RESUME,
    PAUSE,
    SEEK;
}

fun String.getAction(): ActionType {
    return when (this.toUpperCase()) {
        "RESUME" -> ActionType.RESUME
        "PAUSE" -> ActionType.PAUSE
        else -> ActionType.SEEK
    }
}