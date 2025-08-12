package by.AntonDemchuk.blog.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    @NotNull(message = "Username cannot be empty.")
    private String username;

    private String firstname;
    private String lastname;
    private LocalDate birthDate;

    @Email(message = "Email's format is wrong.")
    @NotNull(message = "Email cannot be empty.")
    private String email;

    @Size(min = 4, max = 32, message = "Password size should be between 4 and 32 symbols.")
    private String password;

    @Size(max = 256, message = "Description's too long!")
    private String personalInfo;
}
