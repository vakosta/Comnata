package tv.comnata.comnata.controllers;

import org.springframework.web.bind.annotation.*;
import tv.comnata.comnata.pojo.Cat;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/main")
public class MainController {
    @GetMapping("/{id}")
    @ResponseBody
    public Cat index(HttpServletRequest request, @PathVariable int id) {
        return new Cat(id, "Yo!", 18);
    }
}
