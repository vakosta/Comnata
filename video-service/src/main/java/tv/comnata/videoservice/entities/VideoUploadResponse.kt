package tv.comnata.videoservice.entities

abstract class VideoUploadResponse(
    val status: VideoUploadStatus,
) {
    enum class VideoUploadStatus {
        SUCCESS,
        FAILED,
    }
}

data class VideoUploadResponseSuccess(
    val videoId: String,
    val videoUrl: String,
) : VideoUploadResponse(VideoUploadStatus.SUCCESS)

data class VideoUploadResponseError(
    val message: String,
) : VideoUploadResponse(VideoUploadStatus.FAILED)