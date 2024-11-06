package surfer.backend.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/video")
public class VideoStreamController {

    // Указываем путь к видеофайлу
    private static final String VIDEO_PATH = "" ; // Путь к вашему видеофайлу

    @GetMapping("/stream")
    public void streamVideo(HttpServletResponse response) throws IOException {
        File videoFile = new File(VIDEO_PATH);

        // Проверяем, существует ли файл
        if (!videoFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Video not found");
            return;
        }

        // Устанавливаем тип контента в ответе как видео
        response.setContentType(String.valueOf(MediaType.valueOf("video/mp4")));
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"video.mp4\"");
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(videoFile.length()));

        // Получаем поток для чтения видеофайла
        try (FileInputStream videoStream = new FileInputStream(videoFile);
             ServletOutputStream outStream = response.getOutputStream()) {

            byte[] buffer = new byte[1024 * 8]; // Буфер для передачи данных
            int bytesRead;

            // Читаем файл и передаем его в ответ
            while ((bytesRead = videoStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
                outStream.flush();
            }
        }
    }
}