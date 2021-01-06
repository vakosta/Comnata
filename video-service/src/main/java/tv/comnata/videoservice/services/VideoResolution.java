package tv.comnata.videoservice.services;

import org.jetbrains.annotations.NotNull;

public class VideoResolution implements Comparable<VideoResolution> {
    private final int width;
    private final int height;

    public VideoResolution(String resolution) {
        String[] r = resolution.split("x");
        this.width = Integer.parseInt(r[0]);
        this.height = Integer.parseInt(r[1]);
    }

    public VideoResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return width + "x" + height;
    }

    @Override
    public int compareTo(@NotNull VideoResolution o) {
        return this.width - o.width;
    }
}
