package surfer.backend.mapper;

import org.springframework.stereotype.Component;
import surfer.backend.dto.AuthDto;
import surfer.backend.dto.RegisterResponseDto;
import surfer.backend.entity.User;

@Component
public class UserMapper {

    public User authDtoToEntity(AuthDto dto) {
        return User.builder()
                .username(dto.username())
                .password(dto.password())
                .build();
    }

    public RegisterResponseDto entityToRegisterResponseDto(User user) {
        return new RegisterResponseDto(user.getUsername());
    }

}
