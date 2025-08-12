package by.AntonDemchuk.blog.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private Long expiresIn;
}
