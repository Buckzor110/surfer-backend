//package surfer.backend.controller;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpRange;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import software.amazon.awssdk.core.ResponseInputStream;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.GetObjectRequest;
//
//import java.util.List;
//
//public class BrainrotController {
//
//    private final S3Client s3Client;
//
//    @Value("${yandex.s3.bucket}")
//    private String bucketName;
//
//    public BrainrotController(S3Client s3Client) {
//        this.s3Client = s3Client;
//    }
//
//    @GetMapping("/stream/{videoKey}")
//    public ResponseEntity<InputStreamResource> streamVideo(
//            @RequestHeader(value = "Range", required = false) String rangeHeader,
//            @PathVariable String videoKey) {
//
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                .bucket(bucketName)
//                .key(videoKey)
//                .build();
//
//        ResponseInputStream<?> videoStream = s3Client.getObject(getObjectRequest);
//
//        try {
//            long fileSize = 2L;
//            InputStreamResource videoResource = new InputStreamResource(videoStream);
//
//            if (rangeHeader != null) {
//                List<HttpRange> httpRanges = HttpRange.parseRanges(rangeHeader);
//                HttpRange range = httpRanges.get(0);
//
//                long start = range.getRangeStart(fileSize);
//                long end = range.getRangeEnd(fileSize);
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.add(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize);
//                headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(end - start + 1));
//                headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
//
//                videoStream.skip(start);
//                return new ResponseEntity<>(new InputStreamResource(videoStream), headers, HttpStatus.PARTIAL_CONTENT);
//            }
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize));
//            headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
//            return new ResponseEntity<>(videoResource, headers, HttpStatus.OK);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//}
