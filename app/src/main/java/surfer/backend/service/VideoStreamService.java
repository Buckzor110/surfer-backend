package surfer.backend.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;

@Service
public class VideoStreamService {

    private final S3StorageService s3StorageService;
    private final int countVideos;

    VideoStreamService(S3StorageService s3StorageService) {
        this.s3StorageService = s3StorageService;
        countVideos = s3StorageService.getCount();
    }

    public void streamVideo(HttpServletResponse response) throws IOException {
        try (
             ResponseInputStream<GetObjectResponse> object = s3StorageService.getRandomVideo(countVideos);
             ServletOutputStream outStream = response.getOutputStream()
        ) {
            response.setContentType(String.valueOf(MediaType.valueOf("video/mp4")));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"video.mp4\"");
            response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(object.response().contentLength()));
            byte[] buffer = new byte[1024 * 8];
            int bytesRead = object.read(buffer);

            while (bytesRead != -1) {
                outStream.write(buffer, 0, bytesRead);
                outStream.flush();
                bytesRead = object.read(buffer);
            }
        }
    }
}
