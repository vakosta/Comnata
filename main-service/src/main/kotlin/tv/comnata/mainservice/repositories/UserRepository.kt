package tv.comnata.mainservice.repositories

import org.springframework.data.jpa.repository.JpaRepository
import tv.comnata.mainservice.entities.db.Action
import tv.comnata.mainservice.entities.db.User

interface UserRepository : JpaRepository<User, Long> {
    fun deleteUserByUsername(name: String)

    fun findUserByUsername(name: String): User
    fun findAllByActionsContains(action: Action): Set<User>
    fun findAllByRoomName(roomName: String): List<User>
}