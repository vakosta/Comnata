package tv.comnata.mainservice.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import tv.comnata.mainservice.entities.User
import tv.comnata.mainservice.services.UserService
import tv.comnata.mainservice.services.VideoService

@RestController
class MainController(
    @Autowired
    private val userService: UserService,

    @Autowired
    private val videoService: VideoService
) {
    @GetMapping("/test")
    fun test(): User {
        return userService.getUser()
    }

    @RequestMapping("/createVideo", method = [RequestMethod.PUT])
    fun createVideo(@RequestParam videoUuid: String) {
        videoService.createVideo(videoUuid)
    }

    @PostMapping("/setVideoProgress")
    fun setVideoProgress(@RequestParam videoUuid: String, @RequestParam videoProgress: Int) {
        videoService.setVideoProgress(videoUuid, videoProgress)
    }
}