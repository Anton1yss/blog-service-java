package by.AntonDemchuk.blog.unit.service;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.user.UserLoginDto;
import by.AntonDemchuk.blog.dto.user.UserRegisterDto;
import by.AntonDemchuk.blog.mapper.user.UserRegisterMapper;
import by.AntonDemchuk.blog.repository.UserRepository;
import by.AntonDemchuk.blog.service.AuthService;
import by.AntonDemchuk.blog.unit.BaseServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest extends BaseServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRegisterMapper userRegisterMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void signUpUser_shouldCreateUser() {

        /* Arranging */
        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .password("password")
                .confirmPassword("password")
                .email("email")
                .build();

        given(userRegisterMapper.toEntity(userRegisterDto)).willReturn(mockedUser);
        given(userRepository.save(mockedUser)).willReturn(mockedUser);

        /* Acting */
        User res = authService.signup(userRegisterDto);

        /* Asserting & Verifying */
        assertNotNull(res);
        assertEquals(mockedUser.getEmail(), res.getEmail());
        verify(userRegisterMapper).toEntity(userRegisterDto);
        verify(userRepository).save(mockedUser);
    }

    @Test
    public void logInUser_shouldLogInUser() {

        /* Arranging */
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .email("user@gmail.com")
                .password("password")
                .build();

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getEmail(),
                        userLoginDto.getPassword()
                ))).thenReturn(new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
        when(userRepository.findByEmail(userLoginDto.getEmail())).thenReturn(Optional.of(mockedUser));

        /* Acting */
        User res = authService.authenticate(userLoginDto);

        /* Asserting & Verifying */
        assertEquals(mockedUser, res);
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getEmail(),
                        userLoginDto.getPassword()));
        verify(userRepository).findByEmail(userLoginDto.getEmail());

    }

}
