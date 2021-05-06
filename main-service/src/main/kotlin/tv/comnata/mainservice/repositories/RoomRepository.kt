package tv.comnata.mainservice.repositories

import org.springframework.data.jpa.repository.JpaRepository
import tv.comnata.mainservice.entities.Room

interface RoomRepository : JpaRepository<Room, Long> {
    fun saveAndFlush(room: Room)
    fun findRoomByName(name: String): Room?
}