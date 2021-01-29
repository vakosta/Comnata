package tv.comnata.userservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("")
    public String getUser() {
        return "Ok!";
    }

    @GetMapping("/test")
    public String test() {
        return "User Ok!";
    }
}
