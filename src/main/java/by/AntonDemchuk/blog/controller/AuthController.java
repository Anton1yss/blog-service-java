package by.AntonDemchuk.blog.controller;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.LoginResponse;
import by.AntonDemchuk.blog.dto.UserLoginDto;
import by.AntonDemchuk.blog.dto.UserRegisterDto;
import by.AntonDemchuk.blog.service.AuthService;
import by.AntonDemchuk.blog.service.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth Controller")
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegisterDto registerUserDto) {
        User registeredUser = authService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody UserLoginDto loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(loginResponse.getExpiresIn());


        return ResponseEntity.ok(loginResponse);
    }

}
