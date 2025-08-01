package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.UserLoginDto;
import by.AntonDemchuk.blog.dto.UserRegisterDto;
import by.AntonDemchuk.blog.mapper.UserRegisterMapper;
import by.AntonDemchuk.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRegisterMapper userRegisterMapper;

    public User signup(UserRegisterDto dto) {

        User userToRegister = userRegisterMapper.toEntity(dto);

        userToRegister.setPassword(passwordEncoder.encode(dto.getPassword()));

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
