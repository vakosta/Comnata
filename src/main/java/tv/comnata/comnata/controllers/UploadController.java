package tv.comnata.comnata.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tv.comnata.comnata.entities.Cat;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

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
        String name = "name";

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(name + "-uploaded"));
                stream.write(bytes);
                stream.close();
                return "Вы удачно загрузили " + name + " в " + name + "-uploaded !";
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        }

        return "Ok.";
    }
}
