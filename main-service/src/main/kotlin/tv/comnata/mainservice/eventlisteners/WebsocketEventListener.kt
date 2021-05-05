package tv.comnata.mainservice.eventlisteners

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import tv.comnata.mainservice.services.WebsocketService

@Component
class WebsocketEventListener(
    @Autowired
    private val websocketService: WebsocketService,
) {
    @EventListener
    fun handleSessionConnected(event: SessionConnectEvent) {
        logger.info("Connected!")
    }

    @EventListener
    fun handleSessionDisconnect(event: SessionDisconnectEvent) {
        logger.info("Disconnected!")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(WebsocketEventListener::class.java)
    }
}