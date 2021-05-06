package tv.comnata.videoservice.controllers

import org.slf4j.LoggerFactory
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
    fun getBaseFile(
        response: HttpServletResponse,
        @PathVariable("video_id") videoId: String,
        @PathVariable("file_name") fileName: String,
    ): ResponseEntity<FileSystemResource> {
        logger.info("VIDEO ${videoId.substring(0, 7)} \t BASE FILE $fileName")

        val headers = HttpHeaders()
        response.setHeader("Content-Disposition", String.format("inline; filename=%s", fileName))
        val path = "/tmp/videos/$videoId/$fileName"
        return ResponseEntity(FileSystemResource(path), headers, HttpStatus.OK)
    }

    @GetMapping(value = ["/getVideo/{video_id}/{resolution}/{file_name}"], produces = [MEDIA_TYPE])
    fun getVideoFile(
        response: HttpServletResponse,
        @PathVariable("video_id") videoId: String,
        @PathVariable("resolution") resolution: String,
        @PathVariable("file_name") fileName: String,
    ): ResponseEntity<FileSystemResource> {
        logger.info("VIDEO ${videoId.substring(0, 7)} \t FILE $fileName \t RESOLUTION $resolution")

        val headers = HttpHeaders()
        response.setHeader("Content-Disposition", String.format("inline; filename=%s", fileName))
        val path = "/tmp/videos/$videoId/$resolution/$fileName"
        return ResponseEntity(FileSystemResource(path), headers, HttpStatus.OK)
    }

    @ResponseBody
    @PostMapping("/upload")
    fun uploadFile(
        request: HttpServletRequest,
        @RequestParam file: MultipartFile
    ): VideoUploadResponse {
        logger.info("UPLOAD NEW FILE")
        return videoService.saveVideo(file, "/tmp/videos/")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VideoController::class.java)

        const val MEDIA_TYPE = "application/x-mpegURL"
    }
}