package tv.comnata.videoservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tv.comnata.videoservice.clients.MainClient;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Service
public class VideoService implements FfmpegManager.OnUpdateProgressListener {
    public static final String DIRECTORY_PATH = "videos";

    private static final Logger logger = LoggerFactory.getLogger(VideoService.class);
    private MainClient mainClient;

    @Autowired
    public void setMainClient(MainClient mainClient) {
        this.mainClient = mainClient;
    }

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

        String videoUuid = UUID.randomUUID().toString().replace("-", "");
        String type = "." + separatedName[separatedName.length - 1];

        mainClient.createVideo(videoUuid);

        if (!file.isEmpty() && separatedName.length > 1) {
            try {
                createWorkDirectories(realPath, videoUuid);

                file.transferTo(new File(realPath + videoUuid + "/" + "original" + type));
                FfmpegManager ffmpegManager =
                        new FfmpegManager(realPath + videoUuid + "/", "original" + type, this);
                ffmpegManager.start();

                return "Вы успешно загрузили файл " + videoUuid + type;
            } catch (Exception e) {
                return "Вам не удалось загрузить " + videoUuid + type + " => " + e.getMessage();
            }
        }

        return "Файл пустой.";
    }

    @Override
    public void onUpdatePercent(String videoUuid, double percent) {
        logger.debug(String.format("Progress: %.2f%%%n", percent));

        mainClient.setVideoProgress(videoUuid, (int) (percent * 100));
    }
}
