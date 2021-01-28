package tv.comnata.videoservice.controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tv.comnata.videoservice.services.VideoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/video")
public class VideoController {
    private VideoService videoService;

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/test")
    public String test2() {
        return "Ok!";
    }

    @GetMapping(value = "/getVideo/{video_id}/{file_name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getFile(
            HttpServletResponse response,
            @PathVariable("video_id") String videoId,
            @PathVariable("file_name") String fileName
    ) {
        try {
            // get your file as InputStream
            InputStream is = new FileInputStream(String.format("/tmp/videos/%s/720p/%s", videoId, fileName));

            // copy it to response's OutputStream
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @PostMapping("/upload")
    public String uploadFile(
            HttpServletRequest request,
            @RequestParam MultipartFile file
    ) {
        return videoService.saveVideo(file, "/tmp/videos/");
    }
}
