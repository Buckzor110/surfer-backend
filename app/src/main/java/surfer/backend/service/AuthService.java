package surfer.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import surfer.backend.dto.LoginDto;
import surfer.backend.dto.LoginResponseDto;
import surfer.backend.dto.RegisterDto;
import surfer.backend.entity.Role;
import surfer.backend.entity.User;
import surfer.backend.mapper.UserMapper;
import surfer.backend.utils.JwtUtil;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User register(RegisterDto dto) {
        User user = userMapper.registerDtoToEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.GUEST);
        return userService.createUser(user);
    }

    public LoginResponseDto login(LoginDto dto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
        User user = userService.findByUsername(dto.username());
        return new LoginResponseDto(jwtUtil.generateToken(user));
    }

}