package tv.comnata.mainservice.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class WebsocketController(
    @Autowired
    private val messagingTemplate: SimpMessagingTemplate
) {
}