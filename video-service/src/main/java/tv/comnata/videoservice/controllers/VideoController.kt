package tv.comnata.videoservice.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tv.comnata.videoservice.entities.VideoUploadResponse
import tv.comnata.videoservice.services.VideoService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@CrossOrigin(origins = ["*"])
class VideoController(
    @Autowired
    private var videoService: VideoService,
) {
    @GetMapping(value = ["/getVideo/{video_id}/{file_name}"], produces = [MEDIA_TYPE])
    fun getFile(
        response: HttpServletResponse,
        @PathVariable("video_id") videoId: String,
        @PathVariable("file_name") fileName: String
    ): ResponseEntity<FileSystemResource> {
        val headers = HttpHeaders()
        response.setHeader("Content-Disposition", String.format("inline; filename=%s", fileName))
        val path = String.format("/tmp/videos/%s/360p/%s", videoId, fileName)
        return ResponseEntity(FileSystemResource(path), headers, HttpStatus.OK)
    }

    @ResponseBody
    @PostMapping("/upload")
    fun uploadFile(
        request: HttpServletRequest,
        @RequestParam file: MultipartFile
    ): VideoUploadResponse {
        return videoService.saveVideo(file, "/tmp/videos/")
    }

    companion object {
        const val MEDIA_TYPE = "application/x-mpegURL"
    }
}