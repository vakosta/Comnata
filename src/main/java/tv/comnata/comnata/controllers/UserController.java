package tv.comnata.comnata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tv.comnata.comnata.entities.User;
import tv.comnata.comnata.services.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User index(
            HttpServletRequest request,
            @PathVariable int id
    ) {
        return userService.getUserById(id);
    }
}
