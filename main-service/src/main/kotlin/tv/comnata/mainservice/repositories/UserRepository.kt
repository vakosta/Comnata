package tv.comnata.mainservice.repositories

import org.springframework.data.jpa.repository.JpaRepository
import tv.comnata.mainservice.entities.User

interface UserRepository : JpaRepository<User, Int> {
    fun findByUsername(username: String): User
}