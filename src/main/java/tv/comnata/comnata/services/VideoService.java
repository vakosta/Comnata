package tv.comnata.comnata.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.UUID;

@Service
public class VideoService {
    private static final String DIRECTORY_PATH = "videos";

    private void createDirectoryIfNotExists() {
        File theDir = new File(DIRECTORY_PATH);
        if (!theDir.exists())
            theDir.mkdirs();
    }

    public String saveVideo(MultipartFile file) {
        String[] separatedName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");

        String name = UUID.randomUUID().toString().replace("-", "");
        String type = "." + separatedName[separatedName.length - 1];

        if (!file.isEmpty() && separatedName.length > 1) {
            try {
                createDirectoryIfNotExists();

                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(DIRECTORY_PATH + "/" + name + type));
                stream.write(bytes);
                stream.close();

                return "Вы успешно загрузили файл " + name + type;
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + type + " => " + e.getMessage();
            }
        }

        return "Файл пустой.";
    }
}
