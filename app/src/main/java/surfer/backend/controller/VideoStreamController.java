package surfer.backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import surfer.backend.service.VideoStreamService;

import java.io.IOException;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoStreamController {

    private final VideoStreamService videoStreamService;

    @GetMapping("/stream")
    public void streamVideo(HttpServletResponse response) throws IOException {
        videoStreamService.streamVideo(response);
    }
}