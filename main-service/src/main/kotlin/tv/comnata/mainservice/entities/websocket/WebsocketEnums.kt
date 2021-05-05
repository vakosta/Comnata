package tv.comnata.mainservice.entities.websocket

enum class ActionType {
    RESUME,
    PAUSE,
    SEEK,
}

enum class Reaction {
    GOOD,
    OMG,
    ANGRY,
}

fun String.getActionType(): ActionType {
    return when (this.toUpperCase()) {
        "RESUME" -> ActionType.RESUME
        "PAUSE" -> ActionType.PAUSE
        else -> ActionType.SEEK
    }
}

fun String.getReaction(): Reaction {
    return when (this.toUpperCase()) {
        "GOOD" -> Reaction.GOOD
        "OMG" -> Reaction.OMG
        else -> Reaction.ANGRY
    }
}