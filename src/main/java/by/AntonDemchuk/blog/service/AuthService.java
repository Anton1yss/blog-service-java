package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.user.UserLoginDto;
import by.AntonDemchuk.blog.dto.user.UserRegisterDto;
import by.AntonDemchuk.blog.exception.RegistrationException;
import by.AntonDemchuk.blog.mapper.user.UserRegisterMapper;
import by.AntonDemchuk.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRegisterMapper userRegisterMapper;

    public User signup(UserRegisterDto dto) {

        User userToRegister = userRegisterMapper.toEntity(dto);

        if(dto.getPassword().equals(dto.getConfirmPassword())) {
            userToRegister.setPassword(passwordEncoder.encode(dto.getPassword()));

        } else throw new RegistrationException("Passwords do not match.");

        return userRepository.save(userToRegister);
    }

    public User authenticate(UserLoginDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
