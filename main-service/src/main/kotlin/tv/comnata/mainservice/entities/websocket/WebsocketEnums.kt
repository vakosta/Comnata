package tv.comnata.mainservice.entities.websocket

enum class ActionType {
    RESUME,
    PAUSE,
    SEEK,
    ALL_CLIENTS_READY,
    UNDEFINED,
}

enum class Reaction {
    GOOD,
    OMG,
    ANGRY,
    UNDEFINED,
}

fun String.getActionType(): ActionType {
    return when (this.toUpperCase()) {
        "RESUME" -> ActionType.RESUME
        "PAUSE" -> ActionType.PAUSE
        "SEEK" -> ActionType.SEEK
        else -> ActionType.UNDEFINED
    }
}

fun String.getReaction(): Reaction {
    return when (this.toUpperCase()) {
        "GOOD" -> Reaction.GOOD
        "OMG" -> Reaction.OMG
        "ANGRY" -> Reaction.ANGRY
        else -> Reaction.UNDEFINED
    }
}