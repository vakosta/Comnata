package tv.comnata.videoservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    private void createWorkDirectories(String realPath, String videoId) {
        createDirectoryIfNotExists(realPath);
        createDirectoryIfNotExists(realPath + videoId);
        createDirectoryIfNotExists(realPath + videoId + "/240p");
        createDirectoryIfNotExists(realPath + videoId + "/360p");
        createDirectoryIfNotExists(realPath + videoId + "/480p");
        createDirectoryIfNotExists(realPath + videoId + "/720p");
        createDirectoryIfNotExists(realPath + videoId + "/1080p");
    }

    public String saveVideo(MultipartFile file, String realPath) {
        String[] separatedName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");

        String videoId = UUID.randomUUID().toString().replace("-", "");
        String type = "." + separatedName[separatedName.length - 1];

        if (!file.isEmpty() && separatedName.length > 1) {
            try {
                createWorkDirectories(realPath, videoId);

                file.transferTo(new File(realPath + videoId + "/" + "original" + type));
                FfmpegManager ffmpegManager =
                        new FfmpegManager(realPath + videoId + "/", "original" + type, this);
                ffmpegManager.start();

                return "Вы успешно загрузили файл " + videoId + type;
            } catch (Exception e) {
                return "Вам не удалось загрузить " + videoId + type + " => " + e.getMessage();
            }
        }

        return "Файл пустой.";
    }

    @Override
    public void onUpdatePercent(double percent) {
        System.out.printf("Progress: %.2f%%%n", percent);
    }
}
