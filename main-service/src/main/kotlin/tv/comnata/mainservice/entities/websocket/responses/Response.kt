package tv.comnata.mainservice.entities.websocket.responses

abstract class Response(
    val notificationType: NotificationType,
) {
    enum class NotificationType {
        JOIN,
        VIDEO_ACTION,
        CHAT_MESSAGE,
        REACTION,
    }
}