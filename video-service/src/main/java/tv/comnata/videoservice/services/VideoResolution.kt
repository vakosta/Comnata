package tv.comnata.videoservice.services

class VideoResolution : Comparable<VideoResolution> {
    val width: Int
    val height: Int

    constructor(resolution: String) {
        val r = resolution.split("x".toRegex()).toTypedArray()
        width = r[0].toInt()
        height = r[1].toInt()
    }

    constructor(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    private fun getBandwidth(): Int {
        return when (height) {
            240 -> 246440
            360 -> 460560
            480 -> 836280
            720 -> 2149280
            1080 -> 6221600
            else -> 6221600
        }
    }

    fun getBaseFileText(): String {
        return "#EXT-X-STREAM-INF:" +
                "PROGRAM-ID=1," +
                "BANDWIDTH=${getBandwidth()}," +
                "RESOLUTION=${width}x$height," +
                "NAME=\"$height\"\n" +
                "${height}p/video.m3u8"
    }

    override fun toString(): String {
        return width.toString() + "x" + height
    }

    override fun compareTo(other: VideoResolution): Int {
        return width - other.width
    }
}