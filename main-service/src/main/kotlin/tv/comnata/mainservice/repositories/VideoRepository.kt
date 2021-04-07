package tv.comnata.mainservice.repositories

import org.springframework.data.jpa.repository.JpaRepository
import tv.comnata.mainservice.entities.Video
import java.util.*

interface VideoRepository : JpaRepository<Video, Long> {
    override fun findById(id: Long): Optional<Video>
    fun findByUuid(uuid: String): Video?
}