package tv.comnata.comnata.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tv.comnata.comnata.entities.Cat;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UploadController {
    @GetMapping("/{id}")
    @ResponseBody
    public Cat index(HttpServletRequest request, @PathVariable int id) {
        return new Cat(id, "Yo!", 18);
    }

    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(HttpServletRequest request, @RequestParam MultipartFile file) {
        String[] separatedName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");

        String name = UUID.randomUUID().toString().replace("-", "");
        String type = "." + separatedName[separatedName.length - 1];

        if (!file.isEmpty() && separatedName.length > 1) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream("videos/" + name + type));
                stream.write(bytes);
                stream.close();
                return "Вы удачно загрузили файл " + name + type;
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + type + " => " + e.getMessage();
            }
        }

        return "Файл пустой.";
    }
}
