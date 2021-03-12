package tv.comnata.mainservice.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tv.comnata.mainservice.entities.User
import tv.comnata.mainservice.services.UserService
import tv.comnata.mainservice.services.VideoService

@RestController
@RequestMapping("/main")
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

    @GetMapping("/createVideo")
    fun createVideo(@RequestParam videoUuid: String) {
        videoService.createVideo(videoUuid)
    }

    @GetMapping("/setVideoProgress")
    fun setVideoProgress(@RequestParam videoUuid: String, @RequestParam videoProgress: Int) {
        videoService.setVideoProgress(videoUuid, videoProgress)
    }
}