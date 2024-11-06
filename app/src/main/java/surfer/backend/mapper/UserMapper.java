package surfer.backend.mapper;

import org.springframework.stereotype.Component;
import surfer.backend.dto.RegisterDto;
import surfer.backend.entity.User;

@Component
public class UserMapper {

    public User registerDtoToEntity(RegisterDto dto) {
        return User.builder()
                .username(dto.username())
                .password(dto.password())
                .email(dto.email())
                .build();
    }

}
