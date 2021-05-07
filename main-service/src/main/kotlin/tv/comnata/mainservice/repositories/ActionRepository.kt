package tv.comnata.mainservice.repositories

import org.springframework.data.jpa.repository.JpaRepository
import tv.comnata.mainservice.entities.db.Action

interface ActionRepository : JpaRepository<Action, Long> {
}