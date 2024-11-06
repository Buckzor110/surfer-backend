package surfer.backend.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import surfer.backend.dto.RegisterResponseDto;
import surfer.backend.entity.Role;
import surfer.backend.entity.User;
import surfer.backend.mapper.UserMapper;
import surfer.backend.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public RegisterResponseDto createUser(User user) {
        return userMapper.entityToRegisterResponseDto(userRepository.save(user));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void changeRole(String username, Role role) {
        User user = this.findByUsername(username);
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(findByUsername(username));
        return user.get();
    }
}
