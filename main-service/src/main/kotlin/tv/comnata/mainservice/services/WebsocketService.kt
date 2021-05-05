package tv.comnata.mainservice.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.user.SimpUserRegistry
import org.springframework.stereotype.Service

@Service
class WebsocketService(
    @Autowired
    private val messagingTemplate: SimpMessagingTemplate,

    @Autowired
    private val simpUserRegistry: SimpUserRegistry,
) {
    fun send(url: String, obj: Any) {
        messagingTemplate.convertAndSend(url, obj)
    }

    fun sendToUser(url: String, user: String, obj: Any) {
        messagingTemplate.convertAndSendToUser(user, url, obj)
    }

    fun getNumberOfSessions(): Int {
        return simpUserRegistry.userCount
    }
}