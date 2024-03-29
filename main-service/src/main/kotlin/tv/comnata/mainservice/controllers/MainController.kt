package tv.comnata.mainservice.controllers

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import tv.comnata.mainservice.services.RoomService

@RestController
class MainController(
    @Autowired private val roomService: RoomService,
) {
    @GetMapping("/isRoomExist")
    fun checkIsRoomExist(@RequestParam roomName: String): Boolean {
        return roomService.checkIsRoomExist(roomName)
    }

    @RequestMapping("/createVideo", method = [RequestMethod.PUT])
    fun createVideo(@RequestParam videoUuid: String) {
        roomService.createRoom(videoUuid)
    }

    @PostMapping("/setVideoProgress")
    fun setVideoProgress(@RequestParam videoUuid: String, @RequestParam videoProgress: Int) {
        // roomService.setVideoProgress(videoUuid, videoProgress)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(MainController::class.java)
    }
}
