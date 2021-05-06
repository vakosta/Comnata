package tv.comnata.mainservice.repositories

import org.springframework.data.jpa.repository.JpaRepository
import tv.comnata.mainservice.entities.User

interface UserRepository : JpaRepository<User, Long> {
    fun deleteUserByUsername(name: String)

    fun findUserByUsername(name: String): User
    fun findAllByRoomName(roomName: String): List<User>
}