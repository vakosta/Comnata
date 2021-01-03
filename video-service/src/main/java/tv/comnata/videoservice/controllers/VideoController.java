package tv.comnata.videoservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tv.comnata.videoservice.services.VideoService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class VideoController {
    private VideoService videoService;

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/test")
    public String test() {
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
