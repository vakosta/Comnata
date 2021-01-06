package tv.comnata.videoservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.UUID;

@Service
public class VideoService implements FfmpegManager.OnUpdateProgressListener {
    public static final String DIRECTORY_PATH = "videos";

    private void createDirectoryIfNotExists(String realPath) {
        File theDir = new File(realPath);
        if (!theDir.exists())
            theDir.mkdirs();
    }

    private void splitVideoIntoSegments(String path, String fileName) {
        String command = "ffmpeg -i " + path + fileName + " " +
                "-c:a aac -strict experimental -c:v libx264 -s 426x240 -aspect 16:9 -f hls -hls_list_size 0 -hls_time 10 -threads 0 " + path + "240p/" + "video.m3u8 " +
                "-c:a aac -strict experimental -c:v libx264 -s 640x360 -aspect 16:9 -f hls -hls_list_size 0 -hls_time 10 -threads 0 " + path + "360p/" + "video.m3u8 " +
                "-c:a aac -strict experimental -c:v libx264 -s 852x480 -aspect 16:9 -f hls -hls_list_size 0 -hls_time 10 -threads 0 " + path + "480p/" + "video.m3u8 " +
                "-c:a aac -strict experimental -c:v libx264 -s 1280x720 -aspect 16:9 -f hls -hls_list_size 0 -hls_time 10 -threads 0 " + path + "720p/" + "video.m3u8 " +
                "-c:a aac -strict experimental -c:v libx264 -s 1920x1080 -aspect 16:9 -f hls -hls_list_size 0 -hls_time 10 -threads 0 " + path + "1080p/" + "video.m3u8";

        createDirectoryIfNotExists(path + "240p");
        createDirectoryIfNotExists(path + "360p");
        createDirectoryIfNotExists(path + "480p");
        createDirectoryIfNotExists(path + "720p");
        createDirectoryIfNotExists(path + "1080p");

        StringBuilder result = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.redirectErrorStream(true); // This is the important part
            builder.command(command.split(" "));
            Process process = builder.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                result.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String saveVideo(MultipartFile file, String realPath) {
        String[] separatedName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");

        String name = UUID.randomUUID().toString().replace("-", "");
        String type = "." + separatedName[separatedName.length - 1];

        if (!file.isEmpty() && separatedName.length > 1) {
            try {
                createDirectoryIfNotExists(realPath);
                createDirectoryIfNotExists(realPath + name);

                file.transferTo(new File(realPath + name + "/" + "original" + type));

                splitVideoIntoSegments(realPath + name + "/", "original" + type);

                return "Вы успешно загрузили файл " + name + type;
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + type + " => " + e.getMessage();
            }
        }

        return "Файл пустой.";
    }

    @Override
    public void onUpdatePercent(double percent) {
        // TODO: Something
    }
}
