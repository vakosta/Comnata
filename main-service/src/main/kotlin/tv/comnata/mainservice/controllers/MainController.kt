package tv.comnata.mainservice.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {
    @GetMapping("/test")
    fun test(): String {
        return "Ok!"
    }
}