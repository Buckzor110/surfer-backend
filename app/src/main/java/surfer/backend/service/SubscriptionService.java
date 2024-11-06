package surfer.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import surfer.backend.dto.SubscriptionDto;
import surfer.backend.dto.TokenResponseDto;
import surfer.backend.entity.Role;
import surfer.backend.entity.User;
import surfer.backend.utils.JwtUtil;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionService {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public TokenResponseDto subscribe(SubscriptionDto subscriptionDto) {
        userService.changeRole(subscriptionDto.username(), Role.USER);
        User user = userService.findByUsername(subscriptionDto.username());
        return new TokenResponseDto(jwtUtil.generateToken(user));
    }

    public TokenResponseDto unsubscribe(SubscriptionDto subscriptionDto) {
        userService.changeRole(subscriptionDto.username(), Role.GUEST);
        User user = userService.findByUsername(subscriptionDto.username());
        return new TokenResponseDto(jwtUtil.generateToken(user));
    }
}
