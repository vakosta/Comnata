package tv.comnata.videoservice.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FfmpegManager extends Thread {
    private static final VideoResolution[] AVAILABLE_RESOLUTIONS = {
            // new VideoResolution(426, 240),
            new VideoResolution(640, 360),
            // new VideoResolution(852, 480),
            // new VideoResolution(1280, 720),
            // new VideoResolution(1920, 1080),
    };

    private static final String COMMAND_HLS_BASE = "ffmpeg -i %s";
    private static final String COMMAND_HLS_ONE_RESOLUTION = " -c:a aac -strict experimental -c:v libx264 " +
            "-s %s -aspect 16:9 -f hls -hls_list_size 0 -hls_time 10 -threads 0 %sp/video.m3u8";
    private static final String COMMAND_RESOLUTION =
            "ffprobe -v error -select_streams v:0 -show_entries stream=width,height -of csv=s=x:p=0 %s";

    private final String path;
    private final String fileName;
    private final OnUpdateProgressListener listener;

    public FfmpegManager(String path, String fileName, OnUpdateProgressListener listener) {
        this.path = path;
        this.fileName = fileName;
        this.listener = listener;
    }

    private VideoResolution getVideoResolution() throws IOException {
        Process process = Runtime.getRuntime().exec(String.format(COMMAND_RESOLUTION, path + fileName));

        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            in.close();
            return new VideoResolution(inputLine);
        }

        throw new IOException();
    }

    private String getHlsCommand() throws IOException {
        VideoResolution resolution = getVideoResolution();
        StringBuilder command = new StringBuilder(String.format(COMMAND_HLS_BASE, path + fileName));

        for (VideoResolution availableResolution : AVAILABLE_RESOLUTIONS) {
            if (resolution.compareTo(availableResolution) < 0) {
                break;
            }

            command.append(String.format(COMMAND_HLS_ONE_RESOLUTION, availableResolution,
                    path + availableResolution.getHeight()));
        }

        return command.toString();
    }

    @Override
    public void run() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(getHlsCommand().split(" "));
            final Process process = processBuilder.start();
            Scanner sc = new Scanner(process.getErrorStream());

            // Find duration
            Pattern durPattern = Pattern.compile("(?<=Duration: )[^,]*");
            String dur = sc.findWithinHorizon(durPattern, 0);
            if (dur == null) {
                throw new RuntimeException("Could not parse duration.");
            }
            String[] hms = dur.split(":");
            double totalSecs = Integer.parseInt(hms[0]) * 3600
                    + Integer.parseInt(hms[1]) * 60
                    + Double.parseDouble(hms[2]);
            // System.out.println("Total duration: " + totalSecs + " seconds.");

            // Find time as long as possible.
            Pattern timePattern = Pattern.compile("(?<=time=)[\\d:.]*");
            String match;
            String[] matchSplit;
            while (!isInterrupted() && null != (match = sc.findWithinHorizon(timePattern, 0))) {
                matchSplit = match.split(":");
                double progress = (Integer.parseInt(matchSplit[0]) * 3600 +
                        Integer.parseInt(matchSplit[1]) * 60 +
                        Double.parseDouble(matchSplit[2])) / totalSecs;
                listener.onUpdatePercent(progress * 100);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    interface OnUpdateProgressListener {
        void onUpdatePercent(double percent);
    }
}
