package by.AntonDemchuk.blog.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRegisterDto {

    private String username;

    private String firstname;

    private String lastname;

    private LocalDate birthDate;

    private String email;

    private String password;

    private String personalInfo;

}
