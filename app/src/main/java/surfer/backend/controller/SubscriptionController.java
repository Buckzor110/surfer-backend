package surfer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surfer.backend.dto.SubscriptionDto;
import surfer.backend.service.SubscriptionService;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PutMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody SubscriptionDto request) {
        return ResponseEntity.ok(subscriptionService.subscribe(request));
    }

    @PutMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@RequestBody SubscriptionDto request) {
        return ResponseEntity.ok(subscriptionService.unsubscribe(request));
    }

}

