package by.AntonDemchuk.blog.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDto {

    @Email(message = "Email's format is wrong.")
    @NotNull(message = "Email cannot be empty.")
    private String email;

    @NotNull(message = "Password cannot be empty.")
    private String password;
}
