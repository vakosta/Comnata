package tv.comnata.mainservice.stores

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tv.comnata.mainservice.entities.Action
import java.util.concurrent.ConcurrentHashMap

@Component
class ActionsStore {
    @Volatile
    var index = 0L
        get() {
            logger.info("New index — $field")
            field++
            return field
        }

    val waitingActions = ConcurrentHashMap<Long, Action>()

    companion object {
        private val logger = LoggerFactory.getLogger(ActionsStore::class.java)
    }
}
