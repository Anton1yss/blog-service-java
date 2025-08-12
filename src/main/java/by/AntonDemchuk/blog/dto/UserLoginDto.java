package by.AntonDemchuk.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginDto {

    @Email(message = "Email's format is wrong.")
    @NotNull(message = "Email cannot be empty.")
    private String email;

    @NotNull(message = "Password cannot be empty.")
    private String password;
}
