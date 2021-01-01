package tv.comnata.comnata.services;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class VideoService {
    public static final String DIRECTORY_PATH = "videos";

    private void createDirectoryIfNotExists(String realPath) {
        File theDir = new File(realPath);
        if (!theDir.exists())
            theDir.mkdirs();
    }

    private void splitVideoIntoSegments(String path, String fileName) {
        try {
            FFmpeg ffmpeg = new FFmpeg("/path/to/ffmpeg");

            FFmpegBuilder builder = new FFmpegBuilder()

                    .setInput(path + fileName)     // Filename, or a FFmpegProbeResult
                    .overrideOutputFiles(true) // Override the output if it exists

                    .addOutput(path + "output.mp4")   // Filename for the destination
                    .setFormat("mp4")        // Format is inferred from filename, or can be set

                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();
        } catch (IOException e) {
            return;
        }
    }

    public String saveVideo(MultipartFile file, String realPath) {
        String[] separatedName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");

        String name = UUID.randomUUID().toString().replace("-", "");
        String type = "." + separatedName[separatedName.length - 1];

        if (!file.isEmpty() && separatedName.length > 1) {
            try {
                createDirectoryIfNotExists(realPath);

                file.transferTo(new File(realPath + name + type));

                return "Вы успешно загрузили файл " + name + type;
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + type + " => " + e.getMessage();
            }
        }

        return "Файл пустой.";
    }
}
