package tv.comnata.videoservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tv.comnata.videoservice.services.VideoService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/video")
public class VideoController {
    private VideoService videoService;

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("")
    public String test1() {
        return "Ok!";
    }

    @GetMapping("/test")
    public String test2() {
        return "Ok!";
    }

    @GetMapping("/getPart")
    public String getPart(
            @RequestParam String videoId,
            @RequestParam int filePart
    ) {
        return "Здесь можно будет получить файл.";
    }

    @PostMapping("/upload")
    public String uploadFile(
            HttpServletRequest request,
            @RequestParam MultipartFile file
    ) {
        return videoService.saveVideo(file, request.getServletContext()
                .getRealPath("/" + VideoService.DIRECTORY_PATH + "/"));
    }
}
