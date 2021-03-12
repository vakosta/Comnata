package tv.comnata.mainservice.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tv.comnata.mainservice.entities.User
import tv.comnata.mainservice.services.UserService

@RestController
@RequestMapping("/main")
class MainController(
    @Autowired
    private val userService: UserService
) {
    @GetMapping("/test")
    fun test(): User {
        return userService.getUser()
    }
}