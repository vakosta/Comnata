package tv.comnata.comnata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tv.comnata.comnata.entities.User;
import tv.comnata.comnata.services.UserService;
import tv.comnata.comnata.services.VideoService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    private VideoService videoService;
    private UserService userService;

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }

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

    @GetMapping("/getPart")
    @ResponseBody
    public String getPart(
            @RequestParam String videoId,
            @RequestParam int filePart
    ) {
        return "Здесь можно будет получить файл.";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(
            @RequestParam MultipartFile file
    ) {
        return videoService.saveVideo(file);
    }
}
