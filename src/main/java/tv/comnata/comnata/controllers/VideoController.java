package tv.comnata.comnata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tv.comnata.comnata.services.VideoService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    private VideoService videoService;

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
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
            HttpServletRequest request,
            @RequestParam MultipartFile file
    ) {
        return videoService.saveVideo(file, request.getServletContext()
                .getRealPath("/" + VideoService.DIRECTORY_PATH + "/"));
    }
}
