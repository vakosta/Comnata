package tv.comnata.mainservice.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tv.comnata.mainservice.entities.Video
import tv.comnata.mainservice.repositories.RoomRepository
import tv.comnata.mainservice.repositories.VideoRepository

@Service
class VideoService(
    @Autowired
    private val videoRepository: VideoRepository,

    @Autowired
    private val roomRepository: RoomRepository
) {
    fun createVideo(videoUuid: String) {
        val video = Video(
            uuid = videoUuid,
            name = "",
            priority = 1,
            status = 1,
            room = roomRepository.findRoomById(1)!!
        )
    }

    fun setVideoProgress(videoUuid: String, videoProgress: Int) {
        val video = videoRepository.findByUuid(videoUuid)

        if (video != null) {
            video.progress = videoProgress
            videoRepository.save(video)
        }
    }
}