package tv.comnata.mainservice.entities.websocket.responses

abstract class Response(
    val notificationType: NotificationType,
) {
    enum class NotificationType {
        ACTION,
        CHAT_MESSAGE,
    }
}