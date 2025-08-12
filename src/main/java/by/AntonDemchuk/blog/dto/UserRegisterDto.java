package by.AntonDemchuk.blog.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRegisterDto {

    @NotNull(message = "Username cannot be empty.")
    private String username;

    private String firstname;
    private String lastname;
    private LocalDate birthDate;

    @Email(message = "Email's format is wrong.")
    @NotNull(message = "Email cannot be empty.")
    private String email;

    @Size(min = 4, max = 32, message = "Password size should be between 4 and 32 symbols.")
    @NotNull(message = "Password cannot be empty.")
    private String password;

    @Size(min = 4, max = 32, message = "Password size should be between 4 and 32 symbols.")
    @NotNull(message = "Password cannot be empty.")
    private String confirmPassword;

    @Size(max = 256, message = "Description's too long.")
    private String personalInfo;

    @AssertTrue(message = "You must accept the Terms of Use.")
    private Boolean acceptTerms;

}
