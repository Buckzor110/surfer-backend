package surfer.backend.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/video")
public class VideoStreamController {

    private final S3Client s3Client;

    @Value("${yandex.s3.bucket}")
    private String bucketName;

    public VideoStreamController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping("/stream")
    public void streamVideo(HttpServletResponse response) throws IOException {

        ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);
        List<S3Object> objects = listResponse.contents();

        Random random = new Random();
        S3Object randomObject = objects.get(random.nextInt(objects.size()));

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(randomObject.key())
                .build();

        var object = s3Client.getObject(getObjectRequest);

        response.setContentType(String.valueOf(MediaType.valueOf("video/mp4")));
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"video.mp4\"");
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(object.response().contentLength()));

        try (ResponseInputStream<?> videoStream = s3Client.getObject(getObjectRequest);
             ServletOutputStream outStream = response.getOutputStream()) {

            byte[] buffer = new byte[1024 * 8];
            int bytesRead;

            while ((bytesRead = videoStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
                outStream.flush();
            }
        }
    }
}