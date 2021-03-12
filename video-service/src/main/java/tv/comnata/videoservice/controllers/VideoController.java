package tv.comnata.videoservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tv.comnata.videoservice.services.VideoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/video")
public class VideoController {
    public static final String MEDIA_TYPE = "application/x-mpegURL";

    private VideoService videoService;

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }

    @ResponseBody
    @GetMapping("/test")
    public String test2() {
        return "Ok!";
    }

    @GetMapping(value = "/getVideo/{video_id}/{file_name}", produces = MEDIA_TYPE)
    public ResponseEntity<FileSystemResource> getFile(
            HttpServletResponse response,
            @PathVariable("video_id") String videoId,
            @PathVariable("file_name") String fileName
    ) {
        final HttpHeaders headers = new HttpHeaders();
        response.setHeader("Content-Disposition", String.format("inline; filename=%s", fileName));

        String path = String.format("/tmp/videos/%s/360p/%s", videoId, fileName);
        return new ResponseEntity<>(new FileSystemResource(path), headers, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/upload")
    public String uploadFile(
            HttpServletRequest request,
            @RequestParam MultipartFile file
    ) {
        return videoService.saveVideo(file, "/tmp/videos/");
    }
}
