package tv.comnata.videoservice.services

import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import tv.comnata.videoservice.clients.MainClient
import tv.comnata.videoservice.entities.VideoUploadResponse
import tv.comnata.videoservice.entities.VideoUploadResponseError
import tv.comnata.videoservice.entities.VideoUploadResponseSuccess
import tv.comnata.videoservice.services.FfmpegManager.OnUpdateProgressListener
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class VideoService(
    @Autowired
    private var mainClient: MainClient
) : OnUpdateProgressListener {
    private fun createDirectoryIfNotExists(realPath: String) {
        val theDir = File(realPath)
        if (!theDir.exists()) {
            theDir.mkdirs()
        }
    }

    private fun createBaseFile(path: String, videoId: String, videoResolution: VideoResolution) {
        val resolutions = listOf(240, 360, 480, 720, 1080)

        val contentBuilder = arrayListOf<String>()
        contentBuilder.add("#EXTM3U")

        for (resolution in resolutions) {
            if (videoResolution.height >= resolution) {
                contentBuilder.add(
                    VideoResolution(
                        videoResolution.width / videoResolution.height * resolution,
                        resolution
                    ).getBaseFileText()
                )
            }
        }

        val file: Path = Paths.get("$path$videoId/video.m3u8")
        Files.write(file, contentBuilder, StandardCharsets.UTF_8)
    }

    private fun createWorkDirectories(realPath: String, videoId: String) {
        createDirectoryIfNotExists(realPath)
        createDirectoryIfNotExists(realPath + videoId)
        createDirectoryIfNotExists("$realPath$videoId/240p")
        createDirectoryIfNotExists("$realPath$videoId/360p")
        createDirectoryIfNotExists("$realPath$videoId/480p")
        createDirectoryIfNotExists("$realPath$videoId/720p")
        createDirectoryIfNotExists("$realPath$videoId/1080p")
    }

    fun saveVideo(file: MultipartFile, realPath: String): VideoUploadResponse {
        val separatedName = file.originalFilename!!.split(".")
        val videoUuid = UUID.randomUUID().toString().replace("-", "")
        val type = "." + separatedName[separatedName.size - 1]

        return try {
            mainClient.createVideo(videoUuid)
            if (!file.isEmpty && separatedName.size > 1) {
                createWorkDirectories(realPath, videoUuid)
                file.transferTo(File("$realPath$videoUuid/original$type"))

                val ffmpegManager = FfmpegManager("$realPath$videoUuid/", "original$type", this)
                createBaseFile(realPath, videoUuid, ffmpegManager.videoResolution)

                ffmpegManager.start()

                return VideoUploadResponseSuccess(videoUuid, "/video/getVideo/$videoUuid/video.m3u8")
            }
            VideoUploadResponseError("File is empty.")
        } catch (exception: IOException) {
            VideoUploadResponseError(exception.message!!)
        } catch (exception: FeignException) {
            VideoUploadResponseError(exception.message!!)
        }
    }

    override fun onUpdatePercent(videoUuid: String, percent: Double) {
        logger.info("Video $videoUuid: ${"%.2f".format(percent)}%")
        mainClient.setVideoProgress(videoUuid, (percent * 100).toInt())
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VideoService::class.java)

        const val DIRECTORY_PATH = "videos"
    }
}